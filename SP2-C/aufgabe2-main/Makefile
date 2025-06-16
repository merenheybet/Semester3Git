CC = gcc
CFLAGS = -std=c11 -pedantic -Wall -Werror -D_XOPEN_SOURCE=700 -Wconversion -Wuninitialized

.PHONY: all clean

all: sister

clean:
	rm -f sister sister.o connection-fork.o request-http.o

sister.o: sister.c connection.h cmdline.h request.h i4httools.h
	$(CC) $(CFLAGS) -c $<

sister: sister.o connection-fork.o cmdline.o request-http.o i4httools.o
	$(CC) $(CFLAGS) -o $@ sister.o connection-fork.o cmdline.o request-http.o i4httools.o

connection-fork.o: connection-fork.c connection.h request.h cmdline.h
	$(CC) $(CFLAGS) -c $<

request-http.o: request-http.c request.h i4httools.h cmdline.h
	$(CC) $(CFLAGS) -c $<

