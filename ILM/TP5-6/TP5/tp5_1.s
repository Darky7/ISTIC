      .equ KEYBOARD, 0x8000FF00
      .equ CONSOLE, 0x80007040 		##Adresse du coupleur console

	    .data
mess:	.asciz "Hello world from NIOS II processor\n"
	    .align	4
	    .set noat

	    .text
	    .align	4
	    .global	main

main:
	addi    r4, zero, mess
	call    print_string
	call clear
	beq     zero, zero, main

print_string:
	subi	sp, sp, 4
	stw		ra, 0(sp)
	add 	r2, zero, r4
loop:
	ldb	    r3,(r2) 				## On récupère les 8 bits de poids faibles de r2(correspondant au caractère à afficher)
	addi 	r4, r3, 0
	call putc
	beq     r3, zero, eos
	addi    r2, r2, 1
	beq     zero, zero, loop
eos:
	ldw		ra, 0(sp)
	addi	sp, sp, 4
	ret

putc:
	subi	sp, sp, 4
	stw		ra, 0(sp)
	
	movia 	r5, CONSOLE 			##On met l'adresse du coupleur dans un registre ici r5
	stbio   r4,(r5)  				## On ecrit le caractère à afficher dans le coupleur

	ldw		ra, 0(sp)
	addi	sp, sp, 4
	ret

clear:
	subi	sp, sp, 4
	stw		ra, 0(sp)
	
	movia	r5, CONSOLE 			##On met l'adresse du coupleur dans un registre ici r5
	movia 	r6, 0x00000080
	stbio   r6,(r5)  				## On ecrit le caractère à afficher dans le coupleur

	ldw		ra, 0(sp)
	addi	sp, sp, 4
	ret	

getc:
	movia	r4,KEYBOARD
polling:
	ldwio	r5,4(r4)
	beq    	r5,zero,polling
	ldwio	r5,0(r4)
	stwio   r5,0(r4)
	ret

