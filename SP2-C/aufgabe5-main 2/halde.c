#include "halde.h"
#include <errno.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/mman.h>

/// Magic value for occupied memory chunks.
#define MAGIC ((void*)0xbaadf00d)

/// Size of the heap (in bytes).
#define SIZE (1024*1024*1)

/// Memory-chunk structure.
struct mblock {
	struct mblock *next;
	size_t size;
	char memory[];
};

/// Heap-memory area.
static char *memory;

/// Pointer to the first element of the free-memory list.
static struct mblock *head;

/// Helper function to visualise the current state of the free-memory list.
void printList(void) {
	struct mblock *lauf = head;

	// Empty list
	if (head == NULL) {
		char empty[] = "(empty)\n";
		write(STDERR_FILENO, empty, sizeof(empty));
		return;
	}

	// Print each element in the list
	const char fmt_init[] = "(off: %7zu, size:: %7zu)";
	const char fmt_next[] = " --> (off: %7zu, size:: %7zu)";
	const char * fmt = fmt_init;
	char buffer[sizeof(fmt_next) + 2 * 7];

	while (lauf) {
		size_t n = snprintf(buffer, sizeof(buffer), fmt
			, (uintptr_t) lauf - (uintptr_t)memory, lauf->size);
		if (n) {
			write(STDERR_FILENO, buffer, n);
		}

		lauf = lauf->next;
		fmt = fmt_next;
	}
	write(STDERR_FILENO, "\n", 1);
}

void *malloc (size_t size) {
	// if size is zero, return special pointer(NULL) that can be freed
	if(size == 0){
		return NULL;
	}
	// if the memory block isn't initialised(memory is NULL), initialise the memory block with mmap
	// and set head to memory
	if(memory == NULL){
		memory = mmap(NULL, SIZE, PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, -1, 0);
		if(memory == MAP_FAILED){
			return NULL;
		}
		head = (struct mblock *) memory;
		head -> size = SIZE - sizeof(struct mblock);
		head -> next = NULL;
	}

	// if head is NULL, because there is no empty space, set errno
	if(head == NULL){
		errno = ENOMEM;
		return NULL;
	}

	// Flags that check if there is enough space for the given space(found) and also if there is enough space for a new_struct after the given space
	int found = 0;
	int new_struct = 0;
	struct mblock *current = head;
	struct mblock *prev = NULL;

	// find empty space, set found and new_struct
	// if space is found, current points to the beginning of the empty block
	do{
		if(current->size >= size){
			found = 1;
			if(current->size > size + sizeof(struct mblock)){
				new_struct = 1;
			}
			break;
		}
		prev = current;
		current = current -> next;
	}while(current != NULL);

	// not enough space
	if(!found){
		errno = ENOMEM;
		return NULL;
	}

	// remove current from the list of empty blocks
	if(prev != NULL){
		prev->next = current->next;
	}
	else{
		head = current->next;
	}

	// set magic number to the newly allocated space
	current->next = MAGIC;

	// if there is also a place for a new_struct, pointer arithmetic
	if(new_struct){
		struct mblock *temp = head;
		head = (struct mblock *)((char *) current +sizeof(struct mblock) + size);
	       	head->next = temp;
		head->size = current->size - size -sizeof(struct mblock);	
	}
	
	// update space of the allocated block element to the allocated size
	current->size = size;
	// return a pointer that points to the end of the struct block
	return (void *) current->memory;
}

void free (void *ptr) {
	// if null, do nothing
	if(ptr == NULL){
		return;
	}

	// find the place of argument pointer and go to the beginning of the struct
	struct mblock *strct_pointer = (struct mblock *) ((char *) ptr - sizeof(struct mblock));
	// Check if block is allocated with the magic number, if no abort, if yes free
	if(strct_pointer -> next != MAGIC){
		abort();
		return;
	}
	strct_pointer->next = head;
	head = strct_pointer;
}

void *realloc (void *ptr, size_t size) {
	// if NULL pointer is given, behave like malloc
	if(ptr == NULL){
		return malloc(size);
	}
	// if size is 0, behave like free(ptr)
	if(size == 0){
		free(ptr);
		return NULL;
	}
	// allocate new block with new size, check if fails
	void *tmp = malloc(size);
	if(tmp == NULL){
		return NULL;
	}
	// find the beginning of ptr_struct
	struct mblock *strct_pointer = (struct mblock *) ((char *) ptr - sizeof(struct mblock));

	// check if the new size is bigger than the size of the given pointer
	// copy memory
	// free old block
	size_t copy_size = size < strct_pointer->size ? size : strct_pointer->size;
	memcpy(tmp, ptr, copy_size);
	free(ptr);
	return tmp;
}

void *calloc (size_t nmemb, size_t size) {
	// if real_size = nmemb*size == 0, return NULL as special pointer
	if(nmemb == 0 || size == 0){
		return NULL;
	}

	// call malloc with nmemb*size, check if fails
	void *m = malloc(nmemb * size);
	if(m == NULL){
		return NULL;
	}
	// initialise with 0x0 bytewise
	memset(m, 0, nmemb * size);
	return m;

}
