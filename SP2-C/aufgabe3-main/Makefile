CC = gcc
CFLAGS = -std=c11 -pedantic -Wall -Werror -D_XOPEN_SOURCE=700 -Wconversion -Wuninitialized

.PHONY: all clean

all: rush

clean: 
	rm -f rush rush.o

rush.o: rush.c plist.h shellutils.h
	$(CC) $(CFLAGS) -c $<

rush: rush.o plist.o shellutils.o
	$(CC) $(CFLAGS) -o $@ $^
