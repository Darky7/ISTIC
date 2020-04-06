/*
 * TP3 : Exercice 3
 * AGUESSY Onésime
 * DONOU Dario 
 */
public class Pair {
	public int x;
	public int y;

	/*
	 * @param valeur1
	 * @param valeur2
	 */
	public Pair(int valeur1, int valeur2){
		this.x = valeur1;
		this.y = valeur2;
	}

	/*
	 * le constructeur initialise les deux attributs
	 * @param objet p1 qui nous permettra de récupérer les valeurs
	 */
	public Pair(Pair p1){
		this.x = p1.x;
		this.y = p1.y;
	}

	/*
	 * fonction permettant de savoir si 2 objets de type pair sont egaux
	 * (x1,y1)equals(x2,y2) equivaut a [ x1 == x2 && y1 == y2 ]
	 * @param  l'objet de type pair a comparer
	 * @return true si les 2 objets sont egaux et false si non!
	 */
	public boolean egale(Pair objet1) {
		if (objet1==null) {
			return false;
		}
		else {
			if (this.x == objet1.x && this.y == objet1.y) {
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * fonction permettant de savoir si un objet de type pair est plus petit qu'un autre
	 * (x1,y1)less(x2,y2) equivaut a [ x1 < x2 || (x1 == x2 && y1 < y2) ]
	 * @param  l'objet de type pair a comparer
	 * @return true si les valeurs de l'objet courant sont plus petites de l'objet objet1
	 */
	public boolean plusPetit(Pair objet1) {
		if (objet1 == null) {
			return false;
		} else {
			if (this.x < objet1.x || (this.x == objet1.x && this.y < objet1.y)){
				return true;
			} else {
				return false;
			}
		}
	}

	/*
	 * @return affiche  l'etat actuel de l'objet de type pair avec ses 2 valeurs x et y
	 */
	public String toString() {
	    return "[" + this.x + ", " + this.y + "]";
	}
	
}