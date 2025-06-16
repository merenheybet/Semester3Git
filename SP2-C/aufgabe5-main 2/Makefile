CC = gcc
CFLAGS = -std=c11 -pedantic -Wall -Werror -D_GNU_SOURCE

.PHONY: all clean

all: test test-ref

clean:
	rm -f halde halde.o test test-ref test.o test-ref.o

test.o: test.c halde.h
	$(CC) $(CFLAGS) -c test.c

halde.o: halde.c
	$(CC) $(CFLAGS) -c halde.c

test: test.o halde.o
	$(CC) $(CFLAGS) -o test test.o halde.o

test-ref: test.o halde-ref.o
	$(CC) $(CFLAGS -o test-ref test.o halde-ref.o
