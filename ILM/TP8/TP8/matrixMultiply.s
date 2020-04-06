.text
.global main
main:
			movia r3, matrix 		#r3 = Matrice[]
			addi  r4, r0, 4				#r4 = n <- 4

			xor   r5, r5, r5 		#r5 = i <- 0

for1: 
			beq   r5, r4, finFor1
			xor r6, r6, r6 				#r6 = j <- 0
			
for2: 
			beq   r6, r4, finFor2
			xor r7, r7, r7				#r7 = k <- 0
			xor r8, r8, r8				#r8 = sum <- 0

for3: 			
			beq r7, r4, finFor3
			slli r9,r5,4 					# r9 <- 4*4*i 
			slli r10,r7,2 				# r10 <- 4*k 
			add r9,r9,r10 				
			add r9, r9, r3				# r9 <- /adresse MATRICE[i][k]/ 
			ldw r9,0(r9) 					# r9 <- MATRICE[i][k]

			slli r10,r7,4 				# r10 <- 4*4*k 
			slli r11,r6,2 				# r11 <- 4*j 
			add r10,r10,r11 			
			add r10,r10, r3				# r10 <- /adresse MATRICE2[k][j]/ - SIZE(MATRICEA) 
			addi r10,r10,64 			# r10 <- r10 + 4*16 = /adresse MATRICE2[k][j]/ 
			ldw r10,0(r10) 				# r10 <- MATRICE2[k][j]
			mul r9,r9,r10 				# r9 <- MATRICE[i][k] * MATRICE2[k][j] 
			add r8,r8,r9 					# sum <- sum + MATRICE[i][k] * MATRICE2[k][j]
			
			addi r7, r7, 1 				# k++ 
			br for3

finFor3:	
			slli r10,r5,4 				# r10 <- 4*4*i
			slli r11,r6,2 				# r11 <- 4*j 
			add r10,r10,r11 			
			add r10,r10, r3				# r10 <- /adresse MATRICE3[i][j]/ - 2 * SIZE(MATRICEA) 
			addi r10,r10,128 			# r10 <- r10 + 2*4*16 = /adresse MATRICE3[i][j]/ 
			stw r8, 0(r10)				# MATRICE3[i][j] = sum
			
			addi r6, r6, 1 				# j++ 
			br for2

finFor2:	
			addi r5, r5, 1 				# i++ 
			br for1

finFor1:	
			addi r2, r0, r0
			trap
.data
matrix:
.word 0x1
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x1
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x1
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x1

.word 0x1
.word 0x2
.word 0x3
.word 0x4
.word 0x5
.word 0x6
.word 0x7
.word 0x8
.word 0x9
.word 0xa
.word 0xb
.word 0xc
.word 0xd
.word 0xe
.word 0xf
.word 0x0

.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
.word 0x0
