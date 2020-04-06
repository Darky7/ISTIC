			##################################
			#	     L3INFO - TP ARC2        #
			# 	 Manipulation de tableaux    #
			#	Binôme: AGUESSY Onésime		 #
			#		& Donou Dario            #
			##################################
		
			.equ PRINT_INT, 1
			.equ PRINT_STRING, 4
			.equ READ_INT, 5
			.equ EXIT,	10

			.text
			.global main

# Fonction lectureTableau
lectureTableau:
			subi    sp, sp, 12					# 12 octets reserves : variable locale i(int) - tableau et taille
			stw     r16, 8(sp)   				# @taille
			stw     r17, 4(sp)					# @tableau
			stw     r18, 0(sp)					# int i
			
			addi    r18, r0, 0					# r18 contient la variable i
			addi    r16, r4, 0					# r16 contient le tableau
			addi    r17, r5, 0					# r17 contient la variable taille
			
boucle_for1:
			bge     r18, r17, end_boucle_for1	# Si taille = i branchement vers la fin de boucle for
			
			movia   r4, msgEntrer				# Affichage du message msgEntrer
			addi    r2, zero, PRINT_STRING		# Affichage du message msgEntrer
			trap
			
			addi    r2, zero, READ_INT			# On recupere la variable a mettre dans le tableau dans r2
			trap
			
			addi    r19, r16, 0					# On met dans r19 l'adresse de r16
			slli    r20, r18, 2					# [4 x i] pour recuperer dans r20 l'adresse
			add     r19, r16, r20				# r19 recoit @tableau[i]
			stw     r2, 0(r19)					# On charge dans r2 @tableau[i]
			addi    r18, r18, 1					# On incremente la variable i (i++)
			stw     r18, 0(sp)					# On charge la nouvelle variable i dans la pile
			addi    r4, r16, 0					# On met a jour r4 (@tableau)
			
			br boucle_for1						# Branchement en debut de boucle
			
end_boucle_for1:
			ldw     r18, 0(sp)					# Liberation des emplacements reserves dans la pile
			ldw     r16, 8(sp)
			ldw     r17, 4(sp)
			addi    sp, sp, 12
			ret			

# Fonction affichageTableau			
affichageTableau:
			subi    sp, sp, 12					# 12 octets reserves : variable locale i(int) - tableau et taille
			stw     r16, 8(sp)   				# @taille
			stw     r17, 4(sp)					# @tableau
			stw     r18, 0(sp)					# int i
			
			addi    r18, r0, 0					# r18 contient la variable i
			addi    r16, r4, 0					# r16 contient le tableau
			addi    r17, r5, 0					# r17 contient la variable taille
			
boucle_for2:					
			bge     r18, r17, end_boucle_for2	# Si taille = i branchement vers la fin de boucle for
			
			addi    r19, r16, 0					# On met dans r19 l'adresse de r16
			slli    r20, r18, 2					# [4 x i] pour recuperer dans r20 l'adresse
			add     r19, r16, r20				# r19 recoit l'adresse de tableau[i]
			
			ldw     r4, 0(r19)					# On charge dans r4 @tableau[i]
			addi    r2, zero, PRINT_INT			# Affichage de la variable
			trap
			
			addi     r18, r18, 1				# On incremente la variable i (i++)
			stw      r18, 0(sp)					# On charge la nouvelle variable i dans la pile
			addi     r4, r16, 0                 # On met a jour r4 (@tableau)
			
			br boucle_for2
			
end_boucle_for2:
			ldw     r18, 0(sp)					# Liberation des emplacements reserves dans la pile
			ldw     r16, 8(sp)
			ldw     r17, 4(sp)
			addi    sp, sp, 12
			ret	

# Fonction inversionTableau
inversionTableau:
			subi 	sp, sp, 20                  # 20 octets reserves : variable locale i(int) - tableau - taille - tmp
			stw		r20, 16(sp)					# @tableau
			stw		r19, 12(sp)					# tmp
			stw		r18, 8(sp)					# int i
			stw     r17, 4(sp)					# int j
			stw     r16, 0(sp)					# @taille
	
			addi    r17, r5, -1					# j = taille - 1
			
			addi    r18, r0, 0					# i = 0
			addi    r20, r4, 0					# On met dans r20 - r5 (@tableau)
			addi    r16, r5, 0					# On met dans r16 - r4 (@taille)
			addi    r19, r0, 0					# tmp = 0
			
boucle_while:
			
			bge     r18, r17, end_while			# if i < j
			
			slli    r21, r18, 2					# [4 x i] pour recuperer dans r21 l'adresse
			add     r20, r4, r21				# r20 recoit @tableau[i]
			ldw     r19, 0(r20)					# tmp = tableau[i]
						
			slli    r22, r17, 2					# [4 x j] pour recuperer dans r22 l'adresse
			add     r23, r4, r22				# r23 recoit @tableau[j]
			ldw     r24, 0(r23)					# On met dans r24 la valeur de tableau[j]
			stw     r24, 0(r20)					# On met a jour :  tableau[i] = tableau[j] 			
			stw     r19, 0(r23)					# On met a jour :  tableau[j]  = tmp
			
			addi    r18, r18, 1					# On incremente la variable i (i++)
			addi    r17, r17, -1				# On deincremente la variable j (j--)
			stw     r18, 8(sp)					# On charge la nouvelle variable i dans la pile
			stw     r17, 4(sp)					# On charge la nouvelle variable j dans la pile
			br boucle_while
			
end_while:
			ldw     r16, 0(sp)					# Liberation des emplacements reserves dans la pile
			ldw     r17, 4(sp)
			ldw     r18, 8(sp)
			ldw     r19, 12(sp)
			ldw     r20, 16(sp)
			addi    sp, sp, 20
			ret			
			
main:
			# Affichage du message "Lecture du tableau"
			movia	r4, msgLecture
			addi	r2,	zero, PRINT_STRING
			trap
			
			# Lecture du tableau			
			movia   r4, tableau
			addi    r5, zero, 10
			call    lectureTableau
			
			# Affichage du message "Affichage du tableau"
			movia	r4, msgAffiche
			addi	r2,	zero, PRINT_STRING
			trap
			
			# Affichage du tableau			
			movia   r4, tableau
			addi    r5, zero, 10
			call    affichageTableau
			
			
			# Affichage du message "Inversion du tableau"
			movia	r4, msgInversion
			addi	r2,	zero, PRINT_STRING
			trap		
			
			# Inversion Tableau			
			movia   r4, tableau
			addi    r5, zero, 10
			call    inversionTableau
			
			# Affichage du message "Affichage du tableau"
			movia	r4, msgAffiche
			addi	r2,	zero, PRINT_STRING
			trap

			# Affichage du tableau			
			movia   r4, tableau
			addi    r5, zero, 10
			call    affichageTableau
			
			# On rend la main au système.
			addi	r2, zero, EXIT
			trap
	
			.data
msgLecture:   .asciz "Lecture du tableau.\n"
msgAffiche:   .asciz "Affichage du tableau.\n"
msgEntrer:    .asciz "Entrer un nombre :\n"
msgInversion: .asciz "Inversion du tableau\n"

			# Tableau de 10 éléments
tableau:	.word 0, 0, 0, 0, 0, 0, 0, 0, 0, 0