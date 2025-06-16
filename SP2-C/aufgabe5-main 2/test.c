#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "halde.h"

int main(int argc, char *argv[]) {

	// 4 mallocs
	char *m1 = malloc(100*1024);
	printList();
	char *m2 = malloc(80*1024);
	printList();
	char *m3 = malloc(500*1024);
	printList();
	char *m4 = malloc(10*1024);
	printList();

	// 4 frees
	free(m1);
	printList();
	free(m2);
	printList();
	free(m3);
	printList();
	free(m4);
	printList();

	// noch 4 mallocs
	char *m6 = malloc(50*1024);
        printList();
        char *m7 = malloc(40*1024);
        printList();
        char *m8 = malloc(200*1024);
        printList();
        char *m9 = malloc(5*1024);
        printList();

	// malloc(0) Fall Test-Case
	char *m5 = malloc(0);
	printList();
	free(m5);
	printList();

	// ein Beispiel realloc
	char *r3 = realloc(m9, 100*1024);
	printList();
	free(r3);
	printList();

	// ein calloc
	char *c2 = calloc(132,5);
	printList();
	free(c2);
	printList();

	// restliche frees
	free(m6);
	printList();
	free(m7);
	printList();
	free(m8);
	printList();

	exit(EXIT_SUCCESS);
}
