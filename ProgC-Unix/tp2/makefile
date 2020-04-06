CFLAGS = -Wall -g

#
# Link
#

bin/test_list: obj/list.o obj/test_list.o
		gcc -o bin/test_list obj/list.o obj/test_list.o
	
# To be completed

#
# Objets of TP Lists
#

obj/list.o: src/list.c ./include/list.h
		gcc $(CFLAGS) -o obj/list.o -c src/list.c -I ./include
	
# To be completed
obj/test_list.o: src/test_list.c ./include/list.h
		gcc $(CFLAGS) -o obj/test_list.o -c src/test_list.c -I ./include

# To be completed

#
# Listing creation in pdf
#

listing:    a2ps -o listing.ps include/list.h src/list.c src/test_list.c
		    ps2pdf listing.ps
		    rm listing.ps
# To be completed

#
# Remove files
#

clean : rm obj/*.o
		rm bin/test_list
# To be completed

#
# Test the functionality 
#

test :  make
		./bin/test_list < commandes.txt
# To be completed
