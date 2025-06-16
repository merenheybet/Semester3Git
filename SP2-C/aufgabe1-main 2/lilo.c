#include <stdio.h>
#include <stdlib.h>

// TODO: structs, global variables, etc.

// Definition struct as listElement, which saves the value of an element and the
// address(pointer) for the next element.
struct listElement{
    int value;
    struct listElement *next;
};
typedef struct listElement listElement;


// Moduleglobale Variable fuer den Kopf der Liste
static listElement *head;

// Hinweise zur Aufgabe:
// Insert a new, not negative number, if it's not already in the list. Errors return -1
int insertElement(int value) {
	// TODO: implement me!
    if(value < 0){                      // if the value is a negative number, error => return -1
        return -1;
    }

	listElement *newElement;            // create a new listElement
	newElement = (listElement*) malloc(sizeof(listElement));        // allocate the required space
	if(newElement == NULL){
		return -1;	//malloc Error
	}

	if(head == NULL){                   // if the list has no previous elements, then add the value as head
        newElement->value = value;
		head = newElement;
		newElement->next = NULL;
        return value;
	}

    // if the list isn't empty, go through every element until the last one and add the new Element
	else{
		listElement *current = head;
        while(current->next != NULL){
            // checks to make sure that the element isn't already in the list
            if(current->value != value && current->next->value != value){
                current = current->next;
            }
            // if the element is already a part of the list, frees the allocated space and returns -1
            else{
                free(newElement);
                return -1;
            }
        }

        // if the last element isn't the same as the added value then initialise the new element with the gewuenschte
        // value and return the value in the end.
        if (current->value != value){
            current->next = newElement;
            newElement->value = value;
            newElement->next = NULL;
            return newElement->value;
        }

        // if the last element is the same, free allocated space and return -1
        else{
            free(newElement);
            return -1;
        }
	}
}


// removes the oldest value, so in the context of this implementation the element that the head points to
int removeElement(void) {
	// TODO: implement me!
    // if the list is already empty, there is nothing to remove => return -1
    if(head == NULL){
        return -1;
    }

    // Copy the pointer head to another pointer, then temporarily save the value of the Element and then
    // free the allocated space for this element, then return the deleted value.
    else{
        listElement *oldestElement = head;
        int deletedValue = head->value;
        head = oldestElement->next;
        free(oldestElement);
        return deletedValue;
    }
}

// Only for testing purposes, prints the entire list.
// Bitte nicht bewerten.
// Function to print the entire list
//void printFullList(void) {
//    listElement *current = head;
//    while (current != NULL) {
//        printf("%d\n", current->value);
//        current = current->next;
//    }
// }


int main (int argc, char* argv[]) {
	printf("insert 47: %d\n", insertElement(47));
	printf("insert 11: %d\n", insertElement(11));
	printf("insert 23: %d\n", insertElement(23));
	printf("insert 11: %d\n", insertElement(11));

	printf("remove: %d\n", removeElement());
	printf("remove: %d\n", removeElement());

	// TODO: add more tests

    printf("remove: %d\n", removeElement());
    printf("remove: %d\n", removeElement());

    /*  Found a problem, when you try to insert the same number several times so that the current->next->value equal
     *  to the new value is, then it returned the value and not -1 as it should. In order to prevent this added another
     *  condition to Line 44. Now it seems to function properly.
     */

    // Error when adding two identical elements when the list is empty.
    // Fixed by adding another if check that the value isn't equal to the head->value is
    printf("insert 23: %d\n", insertElement(23));
    printf("insert 23: %d\n", insertElement(23));
    //printFullList();
    printf("insert -47: %d\n", insertElement(-47));
    printf("insert 22: %d\n", insertElement(22));

    //printFullList();

	exit(EXIT_SUCCESS);
}
