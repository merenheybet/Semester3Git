#include "cmdline.h"
#include "request.h"
#include <unistd.h>
#include <signal.h>
#include <stdlib.h>

// Best function
int initConnectionHandler(){
	return initRequestHandler();
}

void handleConnection(int clientSock, int listenSock){
	// Stop zombie processes
	struct sigaction action = {
		.sa_handler = SIG_IGN,
	};
	sigaction(SIGCHLD, &action, NULL);

	pid_t p = fork();

	if(p == -1){
		if(close(clientSock) == -1){
			perror("close");
		}
		if(close(listenSock) == -1){
			perror("close");
		}
		perror("fork");
		return;
	}
	if(p == 0){
		// Kind, requestHandle()
		
		//read
		FILE *rx = fdopen(clientSock, "r");

		if(rx == NULL){
			perror("fdopen");
			close(clientSock);
			exit(EXIT_FAILURE);
		}

		int clientSockCopy = dup(clientSock);

		if(clientSockCopy == -1){
			perror("dup");
			fclose(rx);
			close(clientSockCopy);
			exit(EXIT_FAILURE);
		}

		//write
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

		if(close(clientSock) == -1){
			perror("close");
		}

		if(close(clientSockCopy) == -1){
			perror("close");
		}

		// Is it really?
		exit(EXIT_SUCCESS);
	}
	else{}
}
