// Aufgabe 2: wsort
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static int compareStr(void const *a, void const *b){

    const char *cA = *(char const**) a;
    const char *cB = *(char const**) b;

    // strcmp is already an implemented function for string comparison
    return strcmp(cA, cB);
}



int main(int argc, char **argv){
    // an array that consists of char arrays(strings)
    char **lineArray;   // double pointer => an array of char arrays => string array
    int capacity = 10;
    int arraySize = 0;
    char buffer[103];   // first a single line with 100 characters + '\n\ will be saved from stdin

    lineArray = (char **) malloc(capacity * sizeof(char *));
    if(lineArray == NULL){  // malloc Fehler
	    perror("malloc");
        exit(EXIT_FAILURE);
    }

    // This loop should run until EOF is reached
    while(1){

        // fgets returns NULL, if either EOF is reached or fgets function has failed
        // Deswegen braucht man zwei unterschiedliche Fehlerbehandlungen dafür
	    if(fgets(buffer, sizeof(buffer), stdin) == NULL){
		    if(feof(stdin)){
		    	break;
		    }
		    if(ferror(stdin)){
		    	perror("fgets");
		    	exit(EXIT_FAILURE);
		    }
	    }


        // if the current string in buffer has more than (100 chars + \n) then it should be ignored
        // Eine Fehlermeldung wird zu stderr ausgegeben
	    if(strlen(buffer) > 101){
		    fprintf(stderr, "%s\n", "Wort zu lang...ignoriere");
		    int ch;
		    while((ch = getchar()) != '\n' && ch != EOF);   // the rest of that same string will also be ignored until
            continue;                                       // either EOF is reached or \n
	    }

        // if the string is empty (only consists of one \n) it will also be skipped
        if(strlen(buffer) <= 1){
	    	continue;
	    }

        // Index arraySize of lineArray will be allocated the required memory
        lineArray[arraySize] = (char *) malloc((strlen(buffer) + 1) * sizeof(char));
        if(lineArray[arraySize] == NULL){   // malloc Fehler
		perror("malloc");
        	exit(EXIT_FAILURE);
        }

        // The string in the buffer will be copied to lineArray[arraySize]
        strcpy(lineArray[arraySize], buffer);

        // arraySize will be incremented, so that the new line in the input can be saved to the next index of array
        arraySize++;

        // wenn String-Array voll wird, wird mit realloc dafür doppelt so viel Memory allokiert.
        if(arraySize >= capacity){
            capacity *= 2;
            lineArray = (char **) realloc(lineArray, capacity * sizeof(char *));
            if(lineArray == NULL){  // in case of realloc Fehler the initial lineArray will also be freed.
		        perror("realloc");
		        free(lineArray);
                exit(EXIT_FAILURE);
            }
        }
    }

    // The entire lineArray will be sorted with the help of compareStr function.
    qsort(lineArray, arraySize, sizeof(char *), compareStr);

    // The entire array will be outputted to stdout
    for(int i = 0; i < arraySize; i++){
        if(fputs(lineArray[i], stdout) == EOF){ // fputs Fehler
            perror("fputs");
            exit(EXIT_FAILURE);
        }
        free(lineArray[i]); // The allocated space for each index will be freed
    }

    free(lineArray);    // The allocated space for the entire array will be freed


    if(fflush(stdout) == EOF){  // stdout will be flushed to make sure that nothing's gone wrong
        perror("fflush");
        exit(EXIT_FAILURE);
    }


    return EXIT_SUCCESS;    // succesfully ended.
}
