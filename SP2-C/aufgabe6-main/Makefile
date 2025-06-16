CC = gcc
CFLAGS = -std=c11 -pedantic -Wall -Werror -D_XOPEN_SOURCE=700

.PHONY: all clean

all: creeper

clean:
	rm -f creeper creeper.o

creeper.o: creeper.c argumentParser.h
	$(CC) $(CFLAGS) -c creeper.c

creeper: creeper.o argumentParser.o
	$(CC) $(CFLAGS) -o creeper creeper.o argumentParser.o
