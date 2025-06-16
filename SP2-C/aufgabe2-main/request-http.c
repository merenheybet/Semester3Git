#include "i4httools.h"
#include "cmdline.h"
#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>

int initRequestHandler(){

	const char* path = cmdlineGetValueForKey("wwwpath");
	
	// checks if path is not good
	if(path == NULL ||path[0] == ' ' || strlen(path) == 0){
		return -1;
	}

	const char* portString = cmdlineGetValueForKey("port");
	if(portString == NULL){
		return 0;
	}
	
	// convert string from -port to int
	char* endPtr;
	long portLong = strtol(portString, &endPtr, 10);
	if(portLong == 0 || portLong == LONG_MIN || portLong == LONG_MAX){
		perror("strtol");
		return -1;
	}

	if(*endPtr != '\0'){
		fprintf(stderr, "The given port number is not in the right format!\n");
		return -1;
	}

	if(portLong > 65535 || portLong < 0){
		fprintf(stderr, "Invalid port number\n");
		return -1;
	}

	return 0;
}

void handleRequest(FILE *rx, FILE *tx){
	// buffer
	char request[8192];
	// read input from user
	if(fgets(request, sizeof(request), rx) == NULL){
		if(ferror(rx)){
			perror("fgets");
			exit(EXIT_FAILURE);
		}
	}
	
	// check some cases where the request won't be valid
	if(strlen(request) <= 5){
		httpBadRequest(tx);
		// return eof when error
		fclose(tx);
		fclose(rx);
		fprintf(stderr, "Invalid or incomplete request!");
		exit(EXIT_FAILURE);
	}

	if(request[strlen(request) - 1] != '\n' || (request[strlen(request)-2] != '\r' && request[strlen(request) - 1] != '\n')){
		fprintf(stderr, "Buffer overflow");
		exit(EXIT_FAILURE);
	}

	if(strncmp(request, "GET", 3) != 0){
		fprintf(stderr, "GET couldn't be found at the beginning of request");
		httpBadRequest(tx);
		fclose(tx);
		fclose(rx);
		exit(EXIT_FAILURE);
	}

	// Remove trailing \r\n
	char *newline = strchr(request, '\r');
	if (newline) *newline = '\0';
	newline = strchr(request, '\n');
	if (newline) *newline = '\0';

	// Check if the request ends with a valid HTTP version
	if (strstr(request, "HTTP/1.0") == NULL && strstr(request, "HTTP/1.1") == NULL) {
    		fprintf(stderr, "Invalid HTTP Version\n");
    		exit(EXIT_FAILURE);
	}
	
	// saver path after get
	char* pathPtr = request + 4;
	pathPtr = strtok(pathPtr, " ");

	if(pathPtr == NULL){
		fprintf(stderr,"Couldn't tokenize string");
		exit(EXIT_FAILURE);
	}

	// Checks if path starts with '/'. Maybe not that necessary because of checkPath
	if(strncmp(pathPtr, "/", 1) != 0){
		httpBadRequest(tx);
		fclose(tx);
		fclose(rx);
		fprintf(stderr, "Invalid path, should start with '/'");
		exit(EXIT_FAILURE);
	}

	if(checkPath(pathPtr) == -1){
		httpForbidden(tx, pathPtr);
		fclose(tx);
		fclose(rx);
		fprintf(stderr, "Invaild path, outside of the scope of wwwpath");
		exit(EXIT_FAILURE);
	}
	
	// concat global and local path
	char* completePath;
	completePath = (char*) malloc(1 * (sizeof(cmdlineGetValueForKey("wwwpath")) + 8192));
	if(completePath == NULL){
		perror("malloc");
		free(completePath);
		exit(EXIT_FAILURE);
	}

	strcpy(completePath, cmdlineGetValueForKey("wwwpath"));
	strcat(completePath, pathPtr);
	
	FILE* localPtr = fopen(completePath, "r");
	if(localPtr == NULL){
		httpNotFound(tx, completePath);
		fclose(tx);
		fclose(rx);
		fprintf(stderr, "Not a valid file");
		perror("fopen");
		exit(EXIT_FAILURE);
	}
	free(completePath);
	
	// Check if the file pointer points to a regular file -> doesn't work
	int fd = fileno(localPtr);
	if(fd == -1){
		perror("fileno");
		exit(EXIT_FAILURE);
	}

	struct stat file_stat;

	if(fstat(fd, &file_stat) == -1){
		perror("fstat");
		exit(EXIT_FAILURE);
	}

	if(!S_ISREG(file_stat.st_mode)){
		httpBadRequest(tx);
		fflush(tx);
		fclose(tx);
		fclose(rx);
		fprintf(stderr, "Not a regular file");
		exit(EXIT_FAILURE);
	}

	httpOK(tx);
	fflush(tx);
	
	// Write the contents of the requested file
	char buf[1024];
	size_t bytesRead;
	while((bytesRead = fread(buf, 1, sizeof(buf), localPtr)) > 0){
		if(fwrite(buf, 1, bytesRead, tx) != bytesRead){
			perror("fwrite");
			fclose(localPtr);
			exit(EXIT_FAILURE);
		}

		fflush(tx);
	}
	if(ferror(localPtr)){
		perror("fread");
	}

	fclose(localPtr);	
}
