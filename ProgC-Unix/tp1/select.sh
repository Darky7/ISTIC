#!/bin/bash
if [ $# -ne 3 ]
then
	echo "Erreur : il faut 3 arguments"
	exit 1
fi
if [ ! -r $2 ]
then
	echo "Le fichier $2 n'existe pas ou n'est pas lisible"
	exit 1
fi

 echo $1 | cat - $2 | ./prog2 >$3
