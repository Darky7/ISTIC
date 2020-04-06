#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "types.h"
#include "functions.h" // extern functions declarations

// ------------------------------------------------------------------------
// inner functions declarations
// ------------------------------------------------------------------------

// TO BE COMPLETED

//------------------------------------------------------------------------
// global functions definitions
//------------------------------------------------------------------------

/**
   Create and initialize file table of capacity maxfiles

   parameters :
   maxfiles : capacity of file table

   returns : pointer to table or NULL in case of error
 */
listfile_entry *
create_filelist(int maxfiles) {
    int i = 0;
    listfile_entry * file_ptr;
    file_ptr = (listfile_entry*) malloc (sizeof(listfile_entry));
    if(file_ptr != NULL){
        for(i = 0; i < maxfiles; i++){
            file_ptr[i].filename[0] = '\0';
            file_ptr[i].loaded = 0;
        }
    }
   return file_ptr;
}

/**
   this function add the file in filelist
*/

void
filelist_add(listfile_entry * filelist, int i, char filename[]){
	filelist[i].loaded = 1;
	strcpy(filelist[i].filename, filename);
}


/**
   add words from file to table
   - checks if the file has already been loaded
   - updates the file table (if file not already loaded)
   - reads every word in the file (idem)
   - updates the hash table (idem)

   parameters :
   filename   : name of file :)
   filelist   : pointer to table of files
   htable_ptr : pointer to hash table

   returns :
    1 if file already present in table
    2 if no space left in filelist
   -1 if file doesn't exist or can't be read
   -2 if allocation error
    0 if everything ok
*/
int
add_file(char filename[],
	listfile_entry * filelist,
	hash_table * htable_ptr) {
	FILE *ourFile = NULL;
	int i = 0;
  
	while(i != MAX_FILES){
		// if file already exist and present in table
		if(filelist[i].loaded == 1 && strcmp(filelist[i].filename, filename) == 0){
			return 1;
		}else if(filelist[i].loaded == 0 && strcmp(filelist[i].filename, filename) != 0 ){ // file not exist in our table
			// the path of the file (we have to change this for every computer)
			char path[MAX_LENGTH] = "bin/";
			char *pathFilename = strcat(path, filename);
			// we try to open our file
			ourFile = fopen(pathFilename, "r+");
			// file doesn't exist or can't be read
				if(!ourFile){
					return -1;
				}else{
				// we take in the file each word
				char *ourWord = malloc(sizeof(char));
					while(fgets(ourWord, MAX_LENGTH, ourFile)){
						//printf("%s",ourWord);	(verification)
						// hash code of the word -> hashCode
						int code = hashcode(ourWord, strlen(ourWord));				
						// firstword in the wordlist
						word_entry *it = htable_ptr->htable[code].first_word;
						// here the firstword doesn't exist, so we create the firstword with the ourWord
						if(it == NULL){
							word_entry *it = malloc(sizeof(word_entry));
							// allocation dynamique ok
							if(it != NULL){
								strcpy(it->word, ourWord);
								it->in_file = i;
								it->times = 1;
								it->next = NULL;
								htable_ptr->htable[code].first_word = it;
								htable_ptr->htable[code].last_word = it; 
								filelist_add(filelist, i, filename);
							}else {
								return -2;
							}
						}else{
							// here we check if the word exist already in word_entry (if exist it->times++ else we create the word)
							int stop = 1; // condition to stop while
							while(stop){					
								if(strcmp(it->word, ourWord) != 0 && it->in_file != i){
									it = it->next;
									// word exist
								}else if(strcmp(it->word, ourWord) == 0 && it->in_file == i){
									it->times++;
									stop = 0;
								}else if(it == NULL){
									// we create the word
									word_entry *newWord = malloc(sizeof(word_entry));
									// allocation dynamique ok
									if(newWord != NULL){
										strcpy(newWord->word, ourWord);
										newWord->in_file = i;
										newWord->times = 1; 
										newWord->next = NULL;
										htable_ptr->htable[code].last_word = newWord;
										filelist_add(filelist, i, filename);
										stop = 0;
									}else{
										return -2;
									}
								}
							}
						}															
				}				
			fclose(ourFile);
			return 0;			
			}
		}
	i++;
	}
  		if(i == MAX_FILES){
			return 2;
  		}
	 return 0; // all fine
}


/**
   remove file from file table

   parameters :
   filename   : name of file to remove
   filelist   : pointer to table of files
   htable_ptr : pointer to hash table

   returns :
   -1 if file not in table
    0 if file removed
*/
int
remove_file(char filename[],
	    listfile_entry * filelist,
	    hash_table * htable_ptr) {

  	int i = 0;
	word_entry *it = malloc(sizeof(word_entry));
	word_entry *tmp = malloc(sizeof(word_entry));
	
  	while(i != MAX_FILES){
		// if file already exist and present in table
		if(filelist[i].loaded == 1 && strcmp(filelist[i].filename, filename) == 0){
			for(i = 0; i < htable_ptr->hsize; i++){
				it = htable_ptr->htable[i].first_word;
				if(it!= NULL){
					int stop = 1;		
					while(stop){
						if(it->in_file == i){
							// we save it in tmp
							tmp = it->next;	
							// here we remove word_entry					    
							free(it);
							word_entry *it = malloc(sizeof(word_entry));
							it = tmp;
							if(it == NULL){
								stop = 0;
							}
						}else{
							it = it->next;	
						}
					}
				}
			}
			return 0;
		}
		i++;
	}
	if(i == MAX_FILES){
		return -1;
	}

  return 0;
}

/*
  print file table (only loaded files)

  parameters :
   filelist : pointer to table of files
*/
void
print_list(listfile_entry * filelist) {
	int i = 0;
	while( i != MAX_FILES){
		// if file exist in table and already loaded
		if(filelist[i].loaded == 1 && filelist[i].filename != NULL){
			printf(" -> File %s is loaded at position %d \n", filelist[i].filename, i);
		}	
		i++;
	}
}

/**
   free file table

parameters :
   filelist   : pointer to table of files
*/
void
free_filelist(listfile_entry * filelist) {
  free(filelist);
}

// ************************************************************************
// inner functions
// ************************************************************************

// TO BE COMPLETED

