#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <ctype.h>

#include "plist.h"

//Used as a callback function to print information about a process
static int writeInfo(pid_t pid, const char *cmdLine) {
    if (printf("%i", (int) pid) < 0) {
        perror("printf");
    }
    if (printf("%s", ": ") < 0) {
        perror("printf");
    }
    if (printf("%s\n", cmdLine) < 0) {
        perror("printf");
    }
    return 0;
}

//Check if process with certain pid is a zombie
static int zombies(pid_t pid, const char *cmdLine) {
    int status;
    int retval;

    retval = waitpid(pid, &status, WNOHANG);

    if (retval == -1) {
        perror("waitpid");
    }


        //If the process is a zombie, it is removed from plist and his exit status is printed
    else if (retval > 0) {
        char buf[1337] = "";
        removeElement(pid, buf, 1337);
        printf("Exitstatus [%s] = %d\n", buf, status);
        return 0;
    }

    return 0;
}

int main(int argc, char **argv) {
    char input[1338];
    char buf[1000];

    while (1) {
        // Print "<current Directory>: "
        char *directory = getcwd(buf, sizeof(buf));
        if (printf("%s: ", directory) < 0) {
            perror("printf");
        }

        // Reads input from stdin
        if (fgets(input, sizeof(input), stdin) == NULL) {
            if (feof(stdin)) {
                fputs("\n", stdout);
                break;
            }

            if (ferror(stdin)) {
                perror("stdin");
            }
        }

        //Skip lines longer than 1337 (\n inclusive)
        if (strchr(input, '\n') == NULL) {
            fprintf(stderr, "%s\n", "You have exeeded the charcter limit of 1337 charcters (newline inclusive)");
            int ch;
            while ((ch = getchar()) != '\n' && ch != EOF) {}
            continue;
        }

        // remove \n from input
        if (input[strlen(input) - 1] == '\n') {
            input[strlen(input) - 1] = '\0';
        }

        //Skipping empty lines
        int is_empty = 1;
        for (int i = 0; input[i] != '\0'; i++) {
            if (!isspace(input[i])) {
                is_empty = 0;
                break;
            }
        }
        if (is_empty) {
            walkList(zombies);
            continue;
        }


        // Program speichert ausführende Kommando(z.B. echo)
        // für die Arguments ein String Array erstellen (warum 100, keine Idee, zufällig)
        // erste Argument muss immer das Kommando selbst sein
        // alle anderen Argumente bis NULL werden zu arguments[i] gespeichert

        char input_copy[1337];
        strcpy(input_copy, input);
        char *program = strtok(input, " \t");
        char *arguments[100];
        arguments[0] = program;
        int i = 1;
        while (1) {
            arguments[i] = strtok(NULL, " \t");
            if (arguments[i] == NULL) {
                break;
            }
            i++;
        }

        // Check if "&" is in the arguments, if yes replace it with NULL and set background_flag to 1
        // if no do nothing
        int background_flag = 0;
        if (strcmp(arguments[i - 1], "&") == 0) {
            background_flag = 1;
            arguments[i - 1] = NULL;
        }

        // cd Implementation
        if (strcmp(program, "cd") == 0) {
            if (chdir(arguments[1]) == -1) {
                perror("chdir");
            }
            walkList(zombies);
            continue;
        }

        // jobs Implementation
        if (strcmp(program, "jobs") == 0) {
            // TO-DO
            // walklist implementation required
            walkList(writeInfo);
            walkList(zombies);
            continue;
        }

        // Create child process
        pid_t pid = fork();

        // neue PID zu plist hinzufügen
        insertElement(pid, input_copy);
        if (pid == -1) {
            perror("fork");
            exit(EXIT_FAILURE);
        }

            // Child Process
        else if (pid == 0) {
            execvp(program, arguments);
            perror("execv");
            char buff[1337] = "";
            removeElement(pid, buff, 1337);
            continue;
        }

            // Parent Process
        else {
            if (background_flag) {
                walkList(zombies);
                continue;
            } else {
                int status;
                if (waitpid(pid, &status, 0) == -1) {
                    perror("waitpid");
                }
                char buf[1337] = "";
                removeElement(pid, buf, 1337);
                printf("Exitstatus [%s] = %d\n", buf, status);
            }
        }

        walkList(zombies);
    }

    exit(EXIT_SUCCESS);
}

