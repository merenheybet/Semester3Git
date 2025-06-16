#include "connection.h"
#include "cmdline.h"
#include <sys/socket.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <arpa/inet.h>
#include <string.h>
#include <limits.h>
#include <unistd.h>
#include <errno.h>

void static printUsage(){
	fprintf(stderr,"The intended usage is: ./sister -wwwpath=<dir> [-port=<p>]\n");
} 

int main(int argc, char* argv[]){
	if(cmdlineInit(argc, argv) == -1){
		perror("cmdLineInit");
	}
	
	// If initialisation returns -1, there was an error in the usage -> print usage
	if(initConnectionHandler() == -1){
		printUsage();
		if(errno != 0){
			perror("initConnectionHandler");
		}
		exit(EXIT_FAILURE);
	}
	
	//Standard port
	uint16_t port = 1337;

	// Check explicit port
	if(argc >= 3){
		const char* portString = cmdlineGetValueForKey("port");
		if(portString != NULL){
			char* endPtr;
			// strtol müsste hier ohne Fehler funktionieren, da es schon bei initConnectionHandler
			// geprüft wurde.
			long portLong = strtol(portString, &endPtr, 10);
	
			port = (uint16_t) portLong;
		}
	}

	int listenSocket = -1;

	if((listenSocket = socket(AF_INET6, SOCK_STREAM, 0)) == -1){
		perror("socket");
		exit(EXIT_FAILURE);
	}

	// 1-1 abgeschrieben von Uebungsfolien, reusable port adress part
	int flag = 1;
	if(setsockopt(listenSocket, SOL_SOCKET, SO_REUSEADDR, &flag, sizeof(flag)) == -1){
		perror("setsockopt");
		exit(EXIT_FAILURE);
	}

	struct sockaddr_in6 name = {
		.sin6_family = AF_INET6,
		.sin6_port = htons(port),
		.sin6_addr = in6addr_any,
	};

	if(bind(listenSocket, (struct sockaddr *)&name, sizeof(name)) == -1){
		perror("bind");
		exit(EXIT_FAILURE);
	}

	if(listen(listenSocket, SOMAXCONN) == -1){
		perror("listen");
		exit(EXIT_FAILURE);
	}
	
	// Server-Loop: Accept incoming connections and handle them
	while(1){
		int clientSocket = -1;
		if((clientSocket = accept(listenSocket, NULL, NULL)) == -1){
			perror("accept");
			exit(EXIT_FAILURE);
		}

		handleConnection(clientSocket, listenSocket);
	}
}
