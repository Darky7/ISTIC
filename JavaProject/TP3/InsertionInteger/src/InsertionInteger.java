/*
 * TP3 : Exercice 2
 * AGUESSY Onésime
 * DONOU Dario 
 */
import java.util.Scanner;

public class InsertionInteger {
	
	private static final int SIZE_MAX = 10;
	/*
	 *  nombre d'entiers presents dans t, 0 <= size <= SIZE_MAX
	 */
	private int size;
	private int[] array = new int[SIZE_MAX];
	
	public InsertionInteger(){
		this.size = 0;
	}
	
	/*
	 * @return copie de la partie remplie du tableau
	 */
	public int[] toArray() {
		int[] tableauCopie = new int[SIZE_MAX];
		if (size > 0) {
			for (int i = 0; i < this.size; i++) {
				tableauCopie[i] = this.array[i];
			}
		}
		return tableauCopie;
	}
	
	/*
	 * fonction insert
	 * @param    value a inserer
	 * @pre      valeur de array [0 .. size-1] trie par ordre croissant
	 * @return   false si x appartient a array[0..size-1] ou array est complet
	 * 		     true si x n'appartient pas a array[0..size-1]
	 */
	public boolean insert(int value) {
		boolean resultat = false;
		if (this.size == SIZE_MAX) {
			resultat = false;
		} else {
			int i = 0;
			boolean temp = false;
			while (i < this.size && !temp) {
				if (this.array[i] < value) {
					i++;
				} else if (this.array[i] == value) {
					resultat = false;
					temp = true;
				} else {
					this.size ++;
					for (int j = this.size-1; j > i; j--){
						this.array[j] = this.array[j-1];
					}
					this.array[i] = value;
					resultat = true;
					temp = true;
				}
			}
			if (i == this.size) {
				this.array[this.size] = value;
				this.size ++;
				resultat = true;
			}
		}
		return resultat;	
	}
	
	/*
	 * @param scanner
	 * fonction createArray qui remplie notre tableau 
	 * appel : fonction insert 
	 */
	public void createArray(Scanner scanner) {
		int nombre = scanner.nextInt();
		while(nombre != -1) {
			this.insert(nombre);
			nombre = scanner.nextInt();
		}
	}
	
	// fonction d'affichage des donnees String toString
	public String toString() {
		String tableauTrie = "";
		for (int i = 0; i < this.size-1; i++) {
			tableauTrie = tableauTrie + this.array[i] + ", ";
		}
		tableauTrie = tableauTrie + this.array[this.size-1] + "].";
		return "Taille du tableau : "+this.size+"\nLes élements triés : ["+tableauTrie;
	}

}
