#include <signal.h>
#include <stdio.h>

int main(void) {
	struct sigaction action = {
		.sa_sigaction = SIG_IGN,
	};
	sigaction(SIGINT, &action, NULL);

	while (1) {
		fprintf(stderr, ".");
	}
}
