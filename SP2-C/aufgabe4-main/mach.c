#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <pthread.h>
#include "run.h"

static void die(const char *s) {
    perror(s);
    exit(EXIT_FAILURE);
}

static int parse_positive_int_or_die(char *str) {
    errno = 0;
    char *endptr;
    long x = strtol(str, &endptr, 10);
    if (errno != 0) {
        die("invalid number");
    }
    // Non empty string was fully parsed
    if (str == endptr || *endptr != '\0') {
        fprintf(stderr, "invalid number\n");
        exit(EXIT_FAILURE);
    }
    if (x <= 0) {
        fprintf(stderr, "number not positive\n");
        exit(EXIT_FAILURE);
    }
    if (x > INT_MAX) {
        fprintf(stderr, "number too large\n");
        exit(EXIT_FAILURE);
    }
    return (int)x;
}

int main(int argc, char **argv) {
    // TODO: implement me
    if(argc <= 2){
        printf("mach Usage: ./mach <anzahl Threads> <mach-datei>");
        exit(EXIT_FAILURE);
    }

    // Open the mach-File with fopen
    FILE *mach = fopen(argv[2], "r");
    if(mach == NULL){
        perror("fopen");
        exit(EXIT_FAILURE);
    }

    //pthread_t output;
    //pthread_create(&output, NULL, NULL, NULL);

    int thread_count = parse_positive_int_or_die(argv[1]);
    pthread_t tids[thread_count];
    int last_thread = 0;

    char befehl[4096];
    char out[100][20]; // fix this
    if(fgets(befehl, 4097, mach) == NULL){
        //entweder error oder EOF

    }
    pthread_create(&tids[last_thread], NULL, run_cmd(befehl, (char **) out), NULL);

}
