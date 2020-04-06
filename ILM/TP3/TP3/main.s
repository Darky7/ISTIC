			.equ PRINT_INT,		1
			.equ PRINT_STRING,	4
			.equ READ_INT,		5
			.equ EXIT,		10

			.text

# AGUESSY _ DONOU

			####################################
			# Fonction rechercheDichotomique() #
			####################################
rechercheDichotomique:
			subi 	sp, sp, 24				      # 24 octets reserves : variable locale i(int) - tableau et taille
			stw     ra, 0(sp)				      # on sauve ra pour l'appel a notre fonction
			stw     r16, 4(sp)					  # @tab
			stw     r17, 8(sp)					  #	@val
			stw     r18, 12(sp)					  # variable debut
			stw     r19, 16(sp)					  # variable fin
			stw     r20, 20(sp)					  # variable pos
			
			addi    r16, r5, 0					  # r16 contient le tableau tab
			addi    r17, r4, 0					  # r17 contient val
			addi    r18, r6, 0					  # r18 contient debut
			addi    r19, r7, 0                    # r19 contient fin
			addi    r20, r0, 0                    # r20 contient pos
						
			bge     r19, r18, end_if			  # if (debut > fin)
			addi    r2, r0, -1					  # return -1
			br routine_end			
			
end_if:	
			sub	    r21, r19, r18				  # fin - debut  
			srli    r21, r21, 1				      # (fin - debut) / 2
			add     r20, r21, r18				  # pos = debut +  (fin - debut) / 2
			
			slli    r23, r20, 2					  # [4 x pos] pour recuperer dans r23 l'adresse
			add     r24, r16, r23				  # r24 recoit @tab[pos]
			ldw     r25, 0(r24)					  # r25 recoit la valeur de tab[pos]
			
			bne     r25, r17, plus_petit		  # tab[pos] != val branchement vers fin_dicho
			addi	r2, r20, 0
			br routine_end
			
plus_petit:
			bge     r25, r17, plus_grand
			addi    r6, r20, 1					  # pos + 1
			call rechercheDichotomique			  # appel de la fonction rechercheDichotomique
			br routine_end
			
plus_grand:
			addi    r7, r20, -1			          # pos - 1
			call rechercheDichotomique			  # appel de la fonction rechercheDichotomique			
			br routine_end
			
routine_end:
			ldw     ra, 0(sp)					  # Liberation de la pile 
			ldw     r16, 4(sp)					  
			ldw     r17, 8(sp)					  
			ldw     r18, 12(sp)					  
			ldw     r19, 16(sp)					  
			ldw     r20, 20(sp)
			addi 	sp, sp, 24
			
			ret

			##############################
			#       Fonction main()      #
			##############################
			.globl main
main:					
boucle:
			# Imprime "Entrez un nombre: "
			movia	r4, msgNb		
			addi	r2, zero, PRINT_STRING
			trap
			
			# Lit un nombre
			addi	r2, zero, READ_INT
			trap

			addi    r4, r2, 0						 # r4 recoit la valeur entree par l'utilisateur
			movia   r5, tableau						 # r5 recoit le tableau
			addi    r6, r0, 0						 # r6 recoit 0
			addi    r7, r0, 99						 # r7 recoit 99
			call rechercheDichotomique		         # appel de la fonction rechercheDichotomique
						
			addi    r20, r2, 0						 # r20 recoit la valeur retournee par la fonction
			
			bge     r20, r0, else					 # if (pos < 0) branchement vers else
			
			movia   r4, msgErreur					 # Message erreur
			addi	r2, zero, PRINT_STRING
			trap
			
			br boucle
			
else:
			movia   r4, msgPos						 # Message nombre trouve
			addi	r2, zero, PRINT_STRING
			trap
				
			addi    r4, r20, 0						 # r4 recoit le nombre trouve
			addi	r2, zero, PRINT_INT				 # Affichage du nombre 
			trap

			br boucle
			
			.data
	
msgNb:		.asciz "Entrez un nombre: \n"
msgErreur:	.asciz "Nombre non trouve.\n"
msgPos:		.asciz "La position du nombre est: "
