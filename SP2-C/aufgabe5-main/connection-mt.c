#include <stdio.h>
#include <limits.h>
#include <stdlib.h>
#include <errno.h>
#include <pthread.h>
#include <signal.h>
#include <fcntl.h>
#include <unistd.h>
#include "request.h"
#include "cmdline.h"
#include "jbuffer.h"

static BNDBUF* ringpuffer;

// Methode für Arbeiterthreads
static void *workerThread(void *arg){
	// in Endlosschleife Element aus Ringpuffer nehmen und zugehörige Bearbeitung durchführen
	while(1){
		int clientSock = bbGet(ringpuffer);
		
		FILE *rx = fdopen(clientSock, "r");
		if(rx == NULL){
			perror("fdopen");
			close(clientSock);
			exit(EXIT_FAILURE); // unsure
		}

		int clientSockCopy = dup(clientSock);
		if(clientSockCopy == -1){
			perror("dup");
			fclose(rx);
			exit(EXIT_FAILURE);
		}

		FILE *tx = fdopen(clientSockCopy, "w");
		if(tx == NULL){
			perror("fdopen");
			fclose(rx);
			close(clientSockCopy);
			exit(EXIT_FAILURE);
		}

		handleRequest(rx, tx);

		if(fclose(rx) == EOF){
			perror("fclose");
		}

		if(fclose(tx) == EOF){
			perror("fclose");
		}
	}
	return NULL;
}
/**
 * @brief  Initializes the connection-handling module.
 * @note   This function must be invoked after cmdlineInit().
 * @note   It is the responsibility of this function to initialize the
 *         request-handling module by calling initRequestHandler().
 * @return 0 on success, -1 if the command-line arguments are invalid. If a
 *         non-recoverable error occurs during initialization (e.g. a failed
 *         memory allocation), the function does not return, but instead prints
 *         a meaningful error message and terminates the process.
 */
int initConnectionHandler(void){
	// SIGPIPE ignorieren
	sigset_t sigset;
	sigemptyset(&sigset);

	struct sigaction action = {
		.sa_handler = SIG_IGN,
		.sa_mask = sigset,
		.sa_flags = SA_RESTART
	};

	if(sigaction(SIGPIPE, &action, NULL)){
		perror("sigaction");
		return -1;
	}


	// t Arbeiterthreads erzeugen
	int num_threads = 4;
	
	const char* c_num_threads = cmdlineGetValueForKey("threads");  
	if(c_num_threads != NULL){
		char *endPtr;
		long temp = strtol(c_num_threads, &endPtr, 10);
		if(temp == 0 || temp == LONG_MIN || temp == LONG_MAX){
			perror("strtol");
			return -1;
		}
		if(*endPtr != '\0' || temp < 0 || temp > INT_MAX){
			fprintf(stderr, "There is a problem with the provided thread number\n");
			return -1;
		}
		num_threads = (int) temp;
	}
	
	for(int i = 0; i < num_threads; i++){
		pthread_t tid;
		if((errno = pthread_create(&tid, NULL, workerThread, NULL))){
			perror("pthread_create");
			return -1;
		}
		// Fehlerbehandlung
		pthread_detach(tid);
	}

	// Ringpuffer der Größe b initialisieren
	// BNDBUF *bbCreate(size_t size);
	size_t buffer_size = 8;
	const char* c_buffer_size = cmdlineGetValueForKey("bufsize");
	if(c_buffer_size != NULL){
		char *endPtr;
		long temp = strtol(c_buffer_size, &endPtr, 10);
		if(temp == 0 || temp == LONG_MIN || temp == LONG_MAX){
			perror("strtol");
			return -1;
		}
		if(*endPtr != '\0' || temp < 0 || temp > INT_MAX){
			fprintf(stderr, "There is a problem with the provided buffer size\n");
			return -1;
		}
		buffer_size = (size_t) temp;
	}

	ringpuffer = bbCreate(buffer_size);
	if(ringpuffer == NULL){
		fprintf(stderr, "bbCreate error");
		return -1;
	}

	return initRequestHandler();
}

/**
 * @brief Handles an incoming connection.
 *
 * This function should be called from the main module whenever a new connection
 * has been accepted.
 *
 * @param clientSock Socket representing the accepted connection from the
 *                   client. Upon completing the request or as soon as it is no
 *                   longer needed, this socket is closed.
 * @param listenSock Socket the server is listening on. If the implementation
 *                   launches a child process to handle the connection, this
 *                   socket must be closed in that child process.
 */
void handleConnection(int clientSock, int listenSock){
	// Im Hauptthread: zugehörigen Socket-Deskriptor in Ringpuffer eintragen
	bbPut(ringpuffer, clientSock);
}

