### AGUESSY Onesime - DONOU Dario
#### Groupe 3 ####
			.equ PRINT_INT,		1
			.equ PRINT_STRING,	4
			.equ READ_INT,		5
			.equ EXIT,		10

			.text

			##############################
			#    Fonction print_data()   #
			##############################
print_data:
	   # Partie 1
			subi   sp, sp, 8						# 4 octets reserves : struct data *a
			stw    ra, 0(sp)						
			stw    r16, 4(sp)						# @struct data *a
			
			addi   r16, r4, 0						# r16 contient l'adresse de la struct data
			
			movia  r4, msgValeur					# Affiche de msgValeur
			addi   r2, zero, PRINT_STRING	
			trap
			
			ldw    r17, 0(r16)						# r17 recoit la valeur de a->x
			ldw    r18, 4(r16)					    # r18 recoit la valeur de a->y
			
			addi   r4, r17, 0						# r4 recoit la valeur de a->x et on affiche cette valeur
			addi   r2, zero, PRINT_INT
			trap
			
			movia  r4, msgVirgule					# Affiche de msgVirgule
			addi   r2, zero, PRINT_STRING
			trap
			
			addi   r4, r18, 0						# r4 recoit la valeur de a->y et on affiche cette valeur
			addi   r2, zero, PRINT_INT
			trap
			
			ldw    r16, 4(sp)						# Liberation de la pile
			ldw    ra, 0(sp)		
			addi   sp, sp, 8
		ret

			##############################
			#   Fonction compare_data()  #
			##############################
compare_data:
      # Partie 2
			subi   sp, sp, 12						# 12 octets reserves : struct data *a et struct data *b
			stw    ra, 0(sp)						
		    stw    r16, 4(sp)						# @struct data *a
			stw    r17, 8(sp)						# @struct data *b
			
			addi   r16, r4, 0						# r16 contient l'adresse de la struct data *a
			addi   r17, r5, 0						# r16 contient l'adresse de la struct data *b
			
			ldw    r18, 0(r16)						# r18 recoit la valeur de a->x
			ldw    r19, 4(r16)						# r19 recoit la valeur de a->y
			
			ldw    r20, 0(r17)						# r20 recoit la valeur de b->x
			ldw    r21, 4(r17)						# r21 recoit la valeur de b->y
			
			bne    r18, r20, else_if				# if (a->x == b->x) branchement a else_if
			beq    r19, r21, execute_if			    # if (a->y == b->y) branchement a execute_if
			br else_if								# branchement else_if
			
	  execute_if:
			addi   r2, r0, 0						# return 0
			br end
			
	  else_if:
			ble    r20, r18, else_2					# if(b->x > a->x) branchement a else_2
			
			addi   r2, r0, 1					    # return 1
			br end
	  
	  else_2:
		    addi   r2, r0, -1					    # return -1
			br end
	
	  end:
			ldw    r17, 8(sp)					    # Liberation de la pile
			ldw    r16, 4(sp)
			ldw    ra, 0(sp)
			addi   sp, sp, 12
			
      ret

			####################################
			# Fonction rechercheDichotomique() #
			####################################
rechercheDichotomique:
      # Partie 3
			subi   sp, sp, 20							# 16 octets reserves : struct data *val, struct data *tab, int debut, int fin
			stw    ra, 0(sp)							
			stw    r16, 4(sp)							# variable pos
			stw    r17, 8(sp)
			stw    r18, 12(sp)
			stw    r19, 16(sp)							# variable comp
			
			addi   r16, r4, 0							# struct data *val
			addi   r17, r5, 0							# struct data *tab
			addi   r18, r6, 0							# int debut
			addi   r19, r7, 0							# int fin
			addi   r20, r0, 0							# pos
			
			bge    r18, r19, end_if1					# if(debut > fin)
			addi   r2, zero, -1
			br end_re
	end_if1:
			sub    r21, r19, r18                        # fin-debut
			srli   r21, r21, 1  						# (fin-debut)/2
			add    r20, r21, r18                        # pos = debut + (fin-debut) / 2

			slli   r23, r20, 2                          # calcul de decallage 
			add    r24, r17, r23                        # r24 = &tab[pos]
			
			addi   r5, r24, 0
			call compare_data							# Appel de la fonction compare_data
			
			addi   r25, r2, 0							# r25 = r2 ( On recupere dans r25 comp)

			bne    r25, r0, end_if2						# if(comp == 0)
			addi   r2, r20, 0							# return pos
			br end_re
			
	end_if2:
			bge    r25, r0, end_if3						# if(comp > 0)
			addi   r4, r16, 1
			addi   r5, r17, 0
			addi   r6, r20, 1
			call rechercheDichotomique					# Appel de la fonction rechercheDichotomique
			br end_re
			
	end_if3:
			addi   r4, r16, 0
			addi   r5, r17, 0
			addi   r6, r18, 0
			addi   r7, r20, -1
			call rechercheDichotomique					# Appel de la fonction rechercheDichotomique
			br end_re
			# ...
			
	end_re:	
			ldw    r19, 16(sp)						    # Liberation de la pile
			ldw    r18, 12(sp)
			ldw    r17, 8(sp)
			ldw    r16, 4(sp)
			ldw    ra, 0(sp)
			addi   sp, sp, 20
			
			ret

			##############################
			#       Fonction main()      #
			##############################
			.globl main
main:
      # Partie 1
			movia  r4, valeur						 # On recupere @valeur dans r4
			call print_data						     # Appel de la fonction print_data

      # Partie 2
			movia r5, tableau						 # On recupere @tableau dans r5
			addi  r18, r0, 2						 # On met la valeur 2 dans r18
			slli  r19, r18, 2						 # calcul de decallage
			add   r20, r5, r19						 # r20 recoit @tableau[2]
			addi  r5, r20, 0						 # r5 recoit @tableau[2] c-a-d r20
			call compare_data						 # Appel de la fonction compare_data
			
			addi  r21, r2, 0						 # On recupere comp dans r21 (r21 = comp)
			
			addi  r4, r21, 0					     # Affichage de comp
			addi  r2, zero, PRINT_INT
			trap
			
      # Partie 3
			movia r4, valeur						 # On recupere @valeur dans r4 
			movia r5, tableau						 # On recupere @tableau dans r5
			addi  r6, r0, 0							 # On recupere la valeur 0 dans r6
			addi  r7, r0, 99						 # On recupere la valeur 99 dans r7
			call rechercheDichotomique				 # Appel de la fonction compare_data
			
			addi  r21, r2, 0	 					 # r21 = pos
			
			blt  r21, r0, affichemsgErreur			 # if (pos < 0)
	
			movia r4, msgPos						 # Affichage du message msgPos
			addi  r2, zero, PRINT_STRING
			trap
			
			addi  r4, r21, 0					     # Affichage de pos
			addi  r2, zero, PRINT_INT
			trap
			
	  affichemsgErreur:	
			movia r4, msgErreur						 # Affichage du message msgErreur
			addi  r2, zero, PRINT_STRING
			trap

main_exit:
      # exit
			addi		r2, zero, EXIT
			trap

			.data

msgValeur:  .asciz "Valeur: "
msgErreur:	.asciz "Nombre non trouve.\n"
msgPos:		.asciz "La position du nombre est: "
msgVirgule: .asciz ", "

            .align 4
valeur:     .word 14
            .half 3
