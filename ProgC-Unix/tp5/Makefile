CFLAGS = -Wall -g

#
# Link
#

bin/main: obj/main.o obj/hash.o obj/file.o
		gcc -o bin/main obj/main.o obj/hash.o obj/file.o
	
#
# Objets of TP Lists
#

obj/main.o: src/main.c ./include/functions.h ./include/types.h
		gcc $(CFLAGS) -o obj/main.o -c src/main.c -I ./include
	
# To be completed
obj/hash.o: src/hash.c ./include/functions.h ./include/types.h
		gcc $(CFLAGS) -o obj/hash.o -c src/hash.c -I ./include

# To be completed

obj/file.o: src/file.c ./include/functions.h ./include/types.h
		gcc $(CFLAGS) -o obj/file.o -c src/file.c -I ./include

#
# Remove files
#

clean : rm obj/*.o
		rm bin/main


