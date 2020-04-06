.text
.global main
main:
			movia r3, filtreH 			#r3 = filtreH[]
			movia r11, filtreH			#r11 = signalX[]
			movia r2, filtreH 			#r2 = signalY[]
			addi  r7, r0, 3 			#r7 = k <- 3
			addi  r4, r0, 16			#r4 = n <- 16
			addi  r5, r0, 4				#r5 = n <- 4			
			addi  r11, r11, 16			# decalage de H a X[0]	
			addi  r2, r2, 80			# decalage de H a Y[0]



forX: 
			beq  r7, r4, finForX
			xor  r6, r6, r6 			#r6 = i <- 0
			xor  r8, r8, r8				#r8 = sum <- 0
			
forH: 
			beq   r6, r5, finForH

			slli  r9, r6, 2 			# r9 <- 4*i adr ds H pour H[i]
			sub   r10, r7, r6			# k-i

			addi  r6, r6, 1 			# i++ 

			slli  r10, r10, 2 			# r10 <- 4*(k-i) adr ds X pour X[k-i] 

			add   r9, r9, r3			# r9 <- adr de H[i] 
			ldw   r9, 0(r9) 			# r9 <- H[i]

			
			add   r10, r10, r11			# r10 <- adr de X[k-i] 
			ldw   r10, 0(r10) 			# r10 <- X[k-i]
			
			mul   r9, r9, r10 			# r9 <- H[i] * X[k-i] 
			add   r8, r8, r9 			# sum <- sum + H[i] * X[k-i]

			## DEROULAGE DE LA BOUCLE

			slli  r20, r6, 2 			# r9 <- 4*i adr ds H pour H[i]
			sub   r21, r7, r6			# k-i

			addi  r6, r6, 1 			# i++ 

			slli  r21, r21, 2 			# r10 <- 4*(k-i) adr ds X pour X[k-i] 

			add   r20, r20, r3			# r9 <- adr de H[i] 
			ldw   r20, 0(r20) 			# r9 <- H[i]

			
			add   r21, r21, r21			# r10 <- adr de X[k-i] 
			ldw   r21, 0(r21) 			# r10 <- X[k-i]
			
			mul   r20, r20, r21 			# r9 <- H[i] * X[k-i] 
			add   r8, r8, r20 			# sum <- sum + H[i] * X[k-i]

						
			
			br forH

finForH:	
			slli  r10, r7, 2			# r10 <- 4*k adr ds Y pour Y[k] 
			add   r10, r10, r2			# r10 <- adr de Y[k]
			stw   r8, 0(r10)			# signalY[k] = sum

			addi  r7, r7, 1 			# k++ 
			
			br  forX

finForX:	
			addi r2, r0, r0
			trap
		
.data
filtreH:
.word 0xffffffff
.word 0x1
.word 0xffffffff
.word 0x1

signalX:
.word 0xffffffff
.word 0x1
.word 0xffffffff
.word 0x1
.word 0xffffffff
.word 0x1
.word 0xffffffff
.word 0x1
.word 0xffffffff
.word 0x1
.word 0x1
.word 0xffffffff
.word 0xffffffff
.word 0xffffffff
.word 0x1
.word 0xffffffff

signalY:
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

