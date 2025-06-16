#include "plist.h"
#include "shellutils.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <errno.h>
#include <signal.h>
#include <limits.h>

static volatile pid_t foreground_pid = -1; // Used to terminate onyl foreground child processes
static volatile int foreground_done = 0;
static volatile int foreground_status = 0;

static void handleSIGINT(int signum, siginfo_t *sinfo, void *unused) {
    // Makes sure that CTRL+C only affects foreground processes
    // If foreground_pid == -1 than there are no foreground processes
    int save_errno = errno;
    if (foreground_pid != -1) {
        kill(-foreground_pid, SIGINT);
    }
    putchar('\n');
    shPrompt();
    errno = save_errno;
}

static void handleSIGCHLD(int signum, siginfo_t *sinfo, void *unused) {
    int save_errno = errno;
    int status;

    // waits for child if there is a foreground process
    if (foreground_pid != -1) {
        if (waitpid(foreground_pid, &status, 0) == -1) {
            exit(EXIT_FAILURE);
        }
        foreground_status = status;
        foreground_done = 1;
        return;
    }

    // immediate collection of zombie processes
    if (sinfo->si_code != CLD_EXITED) {
        return;
    } else if (waitpid(sinfo->si_pid, &status, 0) == -1) {
        return;
    } else if (!WIFEXITED(status)) {
        return;
    }

    if (plistNotifyEvent(sinfo->si_pid, status) == -1) {
        return;
    }

    errno = save_errno;
}

static void printProcEvent(pid_t pid, const char cmdLine[], int event) {
    fprintf(stderr, "[%d] \"%s\" ", pid, cmdLine);
    if (WIFEXITED(event) != 0)
        fprintf(stderr, "exited with status %d", WEXITSTATUS(event));
    else if (WIFSIGNALED(event) != 0)
        fprintf(stderr, "terminated by signal %d", WTERMSIG(event));
    else if (WIFSTOPPED(event) != 0)
        fprintf(stderr, "stopped by signal %d", WSTOPSIG(event));
    else if (WIFCONTINUED(event) != 0)
        fputs("continued", stderr);
    putc('\n', stderr);
}


static void die(const char message[]) {
    perror(message);
    exit(EXIT_FAILURE);
}


// Callback function for plistIterate to print and remove all zombie processes
static int zombie(const ProcInfo *info, int event) {
    printProcEvent(info->pid, info->cmdLine, event);
    if (plistRemove(info->pid) == -1) {
        fprintf(stderr, "Coludn't remove zombie");
        return -1;
    }
    return 0;
}


static void changeCwd(char *argv[]) {
    if (argv[1] == NULL || argv[2] != NULL) {
        fprintf(stderr, "Usage: %s <dir>\n", argv[0]);
        return;
    }
    if (chdir(argv[1]) != 0)
        perror(argv[0]);
}


static int printProcInfo(const ProcInfo *info) {
    fprintf(stderr, "[%d] \"%s\"\n", info->pid, info->cmdLine);
    return 0;
}


static void printJobs(char *argv[]) {
    if (argv[1] != NULL) {
        fprintf(stderr, "Usage: %s\n", argv[0]);
        return;
    }
    plistIterate(printProcInfo);
}

// waits one Second while all signals are blocked
static void waitSecond() {
    sigset_t newSet, oldSet;

    if (sigfillset(&newSet) == -1) {
        perror("sigfillset");
        return;
    }

    if (sigprocmask(SIG_BLOCK, &newSet, &oldSet) == -1) {
        perror("sigprockmask");
        return;
    }

    sleep(1);

    if (sigprocmask(SIG_SETMASK, &oldSet, NULL) == -1) {
        perror("sigprockmask");
        return;
    }

}

// Not a very appropriate name
static int killChild(const ProcInfo *info) {
    if (kill(info->pid, SIGTERM) == -1) {
        return -1;
    }
    if (plistRemove(info->pid) == -1) {
        fprintf(stderr, "Couldn't remove zombie");
        return -2;
    }
    return 0;
}

static void nuke(char *argv[]) {
    //nuke without parameters -> kill all children
    if (argv[1] == NULL) {
        if (plistIterate(&killChild) == -1) {
            perror("kill");
        }
        waitSecond();
    }
    if (argv[1] != NULL && argv[2] == NULL) {
        // parse argv[1] to int
        char *endPtr;
        long pidLong = strtol(argv[1], &endPtr, 10);
        if (pidLong == 0 || pidLong == LONG_MIN || pidLong == LONG_MAX) {
            perror("strtol");
            return;
        }

        if (*endPtr != '\0') {
            return;
        }

        if (pidLong > INT_MAX || pidLong < 0) {
            return;
        }

        pid_t pidInt = (pid_t) pidLong;

        // We are sadly not allowed to kill other children :(
        const ProcInfo *process = plistGet(pidInt);
        if (process == NULL) {
            fprintf(stderr, "Not a child process of rush! Couldn't be killed!\n");
            return;
        }

        int retValue = killChild(process);

        if (retValue == -1 || retValue == -2) {
            return;
        }

        waitSecond();
    }
}


static void execute(ShCommand *cmd) {

    // block SIGCHLD
    sigset_t newMask, oldMask;
    sigemptyset(&newMask);
    sigaddset(&newMask, SIGCHLD);
    if (sigprocmask(SIG_BLOCK, &newMask, &oldMask) == -1) {
        die("sigprockmask");
    }

    pid_t pid = fork();
    if (pid == -1)
        die("fork");

    // Child process
    if (pid == 0) {
        if (setpgid(0, 0) == -1) {
            die("setpgid");
        }

        // Execute the desired program
        execvp(cmd->argv[0], cmd->argv);
        die(cmd->argv[0]);

    }
    // Parent process
    else {
        if (setpgid(pid, pid) == -1) {
            perror("setgpid");
        }

        if (cmd->background == true) { // Child runs in background
            const ProcInfo *info = plistAdd(pid, cmd->cmdLine, cmd->background);
            if (info == NULL)
                die("plistAdd");
            printProcInfo(info);
        } else {            // Child runs in foreground
            foreground_pid = pid;
            foreground_done = 0;

            // instead of regular waitpid()
            while (!foreground_done) {
                sigsuspend(&oldMask);
            }

            foreground_pid = -1;
            printProcEvent(pid, cmd->cmdLine, foreground_status);
            foreground_status = 0;
        }
    }

    if (sigprocmask(SIG_SETMASK, &oldMask, NULL) == -1) {
        die("sigprockmask");
    }

}

int main(void) {
    // Handle SIGINT
    struct sigaction action = {
            .sa_sigaction = handleSIGINT,
            .sa_flags = SA_RESTART | SA_SIGINFO,
    };
    sigemptyset(&action.sa_mask);
    if (sigaction(SIGINT, &action, NULL) == -1) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    // Handle SIGCHILD
    struct sigaction action2 = {
            .sa_sigaction = handleSIGCHLD,
            .sa_flags = SA_SIGINFO | SA_RESTART,
    };
    sigemptyset(&action2.sa_mask);
    if (sigaction(SIGCHLD, &action2, NULL) == -1) {
        perror("sigaction");
        exit(EXIT_FAILURE);
    }

    for (;;) {

        // Collect zombie processes

        if (plistHandleEvents(&zombie) != 0) {
            fprintf(stderr, "Couln't collect zombies");
            continue;
        }

        // Show prompt
        shPrompt();

        // Read command line
        char cmdLine[MAX_CMDLINE_LEN];
        if (fgets(cmdLine, sizeof(cmdLine), stdin) == NULL) {
            if (ferror(stdin) != 0)
                die("fgets");
            break;
        }

        // Parse command line
        ShCommand *cmd = shParseCmdLine(cmdLine);
        if (cmd == NULL) {
            perror("shParseCmdLine");
            continue;
        }

        // Redirect stdin and out if requested

        int old_in = -2;
        int old_out = -2;

        if (cmd->inFile != NULL) {
            old_in = dup(STDIN_FILENO);
            if (old_in == -1) {
                perror("dup");
                continue;
            }
            int fd = open(cmd->inFile, O_RDWR | O_CREAT | O_TRUNC, 0644);
            if (fd == -1) {
                perror("open");
                continue;
            }
            if (dup2(fd, STDIN_FILENO) == -1) {
                perror("dup2");
                continue;
            }
            if (close(fd) == -1) {
                perror("close");
                continue;
            }
        }

        if (cmd->outFile != NULL) {
            old_out = dup(STDOUT_FILENO);
            if (old_out == -1) {
                perror("dup");
                continue;
            }
            int fd = open(cmd->outFile, O_RDWR | O_CREAT | O_TRUNC, 0644);
            if (fd == -1) {
                perror("open");
                close(fd);
                continue;
            }
            if (dup2(fd, STDOUT_FILENO) == -1) {
                perror("dup2");
                close(fd);
                continue;
            }
            if (close(fd) == -1) {
                perror("close");
                continue;
            }
        }

        // Execute command
        if (cmd->parseError != NULL) {
            if (strlen(cmdLine) > 0)
                fprintf(stderr, "Syntax error: %s\n", cmd->parseError);
        } else if (strcmp("cd", cmd->argv[0]) == 0) {
            changeCwd(cmd->argv);
        } else if (strcmp("jobs", cmd->argv[0]) == 0) {
            printJobs(cmd->argv);
        } else if (strcmp("nuke", cmd->argv[0]) == 0) {
            nuke(cmd->argv);
        } else {
            execute(cmd);
        }

        // Reset file descriptors
        if (old_in != -2) {
            if (dup2(old_in, STDIN_FILENO) == -1) {
                perror("dup2");
                exit(EXIT_FAILURE); //Failed to reset stdin
            }
        }

        if (old_out != -2) {
            if (dup2(old_out, STDOUT_FILENO) == -1) {
                perror("dup2");
                exit(EXIT_FAILURE); //Failed to reset stdout
            }
        }
    }

    putchar('\n');
    return EXIT_SUCCESS;
}
