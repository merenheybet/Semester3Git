CC = gcc
CFLAGS = -std=c11 -pedantic -Wall -Werror -D_XOPEN_SOURCE=700

.PHONY: all clean

all: clash

clean:
	rm -f clash clash.o plist.o

clash.o: clash.c plist.h
	$(CC) $(CFLAGS) -c clash.c

plist.o: plist.c
	$(CC) $(CFLAGS) -c plist.c

clash:	clash.o	plist.o
	$(CC) $(CFLAGS) -o clash clash.o plist.o
