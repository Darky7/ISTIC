#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "functions.h" // global functions declarations

// ------------------------------------------------------------------------
// inner functions declarations
// ------------------------------------------------------------------------

// TO BE COMPLETED

//------------------------------------------------------------------------
// global functions definitions
//------------------------------------------------------------------------

/**
   create and initialize hash table

   returns : pointer to table or NULL if creation error
*/
hash_table *
create_table() {
    int i = 0;
    hash_table* htable_ptr;
    htable_ptr = (hash_table *) malloc (sizeof(hash_table));
    if(htable_ptr!=NULL){
        htable_ptr->hsize = MAX_ENTRIES;
        htable_ptr->htable = malloc(sizeof(word_entry) * htable_ptr->hsize);
        for(i = 0; i < htable_ptr->hsize; i++){
            htable_ptr->htable[i].first_word = NULL;
            htable_ptr->htable[i].last_word = NULL;
        }
    	return htable_ptr;
    } else{
		return NULL;
	}
}

/**
   search a word in table ; print word if found, with number of occurrences
   and file where word is found

   parameters :
   word : the word to look for
   filelist   : pointer to table of files
   htable_ptr : pointer to hash table

   returns : true if found, false otherwise
*/
int
search_word(char word[],
	    listfile_entry * filelist,
	    hash_table * htable_ptr) {
	
	int count = 0;	
	int code = hashcode(word, strlen(word));
	word_entry *it = malloc(sizeof(word_entry));
	it = htable_ptr->htable[code].first_word;
	if(it != NULL){
		while(it != htable_ptr->htable[code].last_word){
			if(strcmp(it->word, word) == 0){
				int filePosition = it->in_file;						
				printf("Word %s is find in file %s", word, filelist[filePosition].filename);
				count = 0;
				it = it->next;
			}else{
				count = 1;
				it = it->next;
			}
		}
		if(count == 1){
			printf("Word %s is not find", word);
			return 0;
		}else{
			return 1;
		}
	}  
	return 0;
}

/**
   print table contents

   parameters :
   htable_ptr : pointer to hash table
   filelist   : pointer to table of files
*/
void
print_table(hash_table * htable_ptr,
	    listfile_entry * filelist) {
   
    int i = 0;
	word_entry *it = malloc(sizeof(word_entry));
	// we check in table wordlist
    for(i = 0; i< htable_ptr->hsize; i++){
		// if first_word is not null
		if(htable_ptr->htable[i].first_word != NULL){
			it = htable_ptr->htable[i].first_word;		
			char* word = malloc(sizeof(char*));
			int stop = 1;
			while(stop){
				word = it->word;
				// filePosition : index of our file
				int filePosition = it->in_file;
				printf(" Word : %s in file %s \n", word, filelist[filePosition].filename);
				it = it->next;
				// condition to stop while
				if(it == NULL){
					stop = 0;
				}else{
					stop = 1;
				}	
			}
		}
  	}

}


/**
   free hash table

   parameters :
   htable_ptr : pointer to hash table
*/
void
free_table(hash_table * htable_ptr) {
  free(htable_ptr);
}

// ------------------------------------------------------------------------
// inner functions definitions
// ------------------------------------------------------------------------

// TO BE COMPLETED
