/**************************************************************************
 * L3Informatique						C/Unix
 * 			TP linked lists
 *
 * Group 	: 3.1
 * Name 1 : AGUESSY Onésime
 * Name 2 : DONOU Dario
 *
 **************************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <termios.h>
#include <unistd.h>
#include "list.h"

/* Compute the number of elements of the list */
int
list_size(list_elem_t * p_list)
{
  int nb = 0;
  while (p_list != NULL) {
    nb += 1;
    p_list = p_list->next;
  }
  return nb;
}

/* Print the elements of the list */
void
print_list(list_elem_t* p_list) {
  list_elem_t * pl = p_list;
  printf("The list contains %d element(s)\n", list_size(p_list));
  while(pl != NULL) {
    printf("[%d]",pl->value);
    pl = pl->next;
    if (pl != NULL) {
      printf("->");
    }
  }
}

/* Compute the number of memory allocations */
extern int nb_malloc;

int
main(int argc, char **argv)
{
  list_elem_t * la_liste = NULL;	// The pointer to the head of the list
  char menu[] =
    "\n Program of chained list \n"\
    "  'h/t' : Insert an element to the head/tail of the list\n"\
    "  'f'   : search of a list element\n"\
    "  's'   : suppression of a list element\n"\
    "  'r'   : reverse the order of the list elements\n"\
    "  'x'   : exit the program\n"\
    " What is your choice ?";
  int choice=0;				// choice from the menu
  int value=0;				// inserted value

  printf("%s",menu);
  fflush(stdout);

  while(1) {
    fflush(stdin);
    choice = getchar();
    printf("\n");

    switch(choice) {
    case 'H' :
    case 'h' :
      printf("Value of the new element ? ");
      scanf("%d",&value);
      if (insert_head(&la_liste,value)!=0) {
	printf("Error : impossible to add the element %d\n",value);
      };
      break;

    case 'T' :
    case 't' :
      printf("Value of the new element ? ");
      scanf("%d",&value);
      if (insert_tail(&la_liste,value)!=0) {
	printf("Error : impossible to add the element %d\n",value);
      };
      break;


    case 'F' :
    case 'f' :
      printf("Index of the element you want to find ? ");
      scanf("%d",&value);
      list_elem_t * elm = find_element(la_liste,value);
      if(elm == NULL){
    printf("The element at this index %d is not found\n",value);
	  }
      else{
	printf("The element at this index %d is [%d]\n",value, elm->value);
	  }
      break;

    case 's' :
    case 'S' :
      printf("Value of the element you want to delete ? ");
      scanf("%d",&value);
      if (remove_element(&la_liste,value)!=0) {
	printf("Error : impossible to delete this element %d\n",value);
      };
      break;


    case 'r' :
    case 'R' :
      reverse_list(&la_liste);
      break;


    case 'x' :
    case 'X' :
      return 0;

    default:
      break;
    }
    print_list(la_liste);

    if (nb_malloc!=list_size(la_liste)) {
      printf("\n Attention : There is a leak memory in your program !\n");
      printf("The list has %d elements, but %d elements are allocated in memory!\n",
	     list_size(la_liste),nb_malloc);
    }
    getchar(); // to consume a return character and avoid double display of the menu
    printf("%s\n",menu);
  }
  return 0;
}



