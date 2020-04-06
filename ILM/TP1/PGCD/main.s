			########################
			#	L3INFO - TP ARC2   #
			# 	 Calcul de PGCD    #
			########################
		
			.equ PRINT_INT, 1
			.equ PRINT_STRING, 4
			.equ READ_INT, 5
			.equ EXIT,	10

			.text
			.global main
		
main:
			# Lecture de l'entier "a"
			movia		r4,	invite_a
			addi		r2, zero, PRINT_STRING
			trap
			
			addi		r2, zero, READ_INT
			trap
			# Ecriture de a dans r5
			addi		r5, r2, 0
			
			# Lecture de l'entier "b"
			movia		r4,	invite_b
			addi		r2, zero, PRINT_STRING
			trap			
			
			addi		r2, zero, READ_INT
			trap
			# Ecriture de b dans r6
			addi		r6, r2, 0
			
			# Calcul du PGCD
while:
			beq 		r5, r6, end_while
			bge 		r5, r6, else
			sub 		r6, r6, r5
			
else:
			sub 		r5, r5, r6
		    br while
			
end_while:
			addi		r20, r5, 0		
		
			# Affichage du resultat
			movia		r4, msg_res
			addi		r2, zero, PRINT_STRING
			trap
			
			addi		r4, r20, 0
			addi		r2, zero, PRINT_INT
			trap
			
			addi		r2, zero, EXIT
			trap

.data
			
a:			.skip 4
b:			.skip 4
resultat:	.word 0
invite_a:	.asciz "Entrez un entier (a): "
invite_b:	.asciz "Entrez un entier (b): "
msg_res:	.asciz "Le PGCD de a et b est: "
