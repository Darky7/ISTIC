/*
 * TP3 : Exercice 3
 * AGUESSY Onésime
 * DONOU Dario 
 */
import java.util.Scanner;
public class InsertionPair {
	private static final int SIZE_MAX = 10;
	private int size;
	private Pair[] array = new Pair[SIZE_MAX];


	public InsertionPair(){
		this.size = 0;
	}

	/*
	 * @return copie de la partie remplie du tableau d'objet Pair
	 */
	public Pair[] toArray(){
		Pair[] tableauCopie = new Pair[SIZE_MAX];
		if (size > 0) {
			for (int i = 0; i < this.size; i++) {
				Pair p = new Pair(this.array[i]);
				tableauCopie[i] = p;
			}
		}
		return tableauCopie;
	}

	/*
	 * insertion d'un objet Pair dans notre tableau d'objets pair
	 * tableau trie avant et apres l'insertion de l'objet 
	 * @param  objet de type pair a inserer
	 * @return true si l'objet est bien ajoute sinon false
	 */
	public boolean insert(Pair objet) {
		boolean result = false;
		if (this.size == SIZE_MAX) {
			result = false;
		} else {
			int i = 0;
			boolean temp = false;
			while (i < this.size && !temp) {
				if (this.array[i].plusPetit(objet)) {
					i++;
				} else if (this.array[i].egale(objet)) {
					temp = true;
				} else {
					this.size ++;
					for (int j = this.size-1; j > i; j--){
						this.array[j] = this.array[j-1];
					}
					this.array[i] = objet;
					result = true;
					temp = true;
				}
			}
			if (i == this.size) {
				this.array[this.size] = objet;
				this.size ++;
				result = true;
			}
			
		}

		return result;
	}
	
	/*
	 * @param scanner
	 * fonction createArray qui remplie notre tableau 
	 * appel : fonction insert 
	 */
	public void createArray(Scanner scanner) {
		int x1 = scanner.nextInt();
		int y1;
		boolean value = false;
		while ((x1 != -1) && !value) {
			y1 = scanner.nextInt();
			if (y1 != -1) {
				Pair p = new Pair(x1, y1);
				this.insert(p);
				x1 = scanner.nextInt();
			} else {
				value = true;
			}
		}
		System.out.println(this.toString());
	}	
	
	// fonction d'affichage des donnees String toString
	public String toString() {
		String tableauTrie = "";
		for (int i = 0; i < this.size-1; i++) {
			tableauTrie = tableauTrie + this.array[i].toString() + ", ";
		}
		tableauTrie = tableauTrie + this.array[this.size-1].toString() + "].";
		return "Taille du tableau : "+this.size+"\nLes élements triés : ["+tableauTrie;
	}

}
