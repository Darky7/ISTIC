public class SmallSet {
	private boolean[] tab= new boolean[256];
	
	public SmallSet() {
		for(int i = 0 ; i <= 255; ++i){
			tab[i] = false;
		}
	}
	
	public SmallSet(boolean t[]) {
		for(int i = 0; i <= 255; ++i){
			tab[i] = t[i];
		}
	}
	
	/**
	 * @return nombre de valeurs appartenant a l'ensemble
	 */
	public int size() {
		int count = 0, i = 0;
			for(int i=0; i<256; i++){
				if(tab[i])
				count++;
			}
		return count;
	}
	
	/**
	 * @param x valeur à tester
	 * @pre 0 <= x <= 255
	 * @return true, si l'entier x appartient à l'ensemble, false sinon
	 */
	public boolean contains(int x) {
		if(x < 0 || x > 256) {
			return false;
		}else {
			return tab[x];
		}
	}	
	
	/**
	 * @return true, si l'ensemble est vide, false sinon
	 */
	public boolean isEmpty() {
		for(int i = 0; i < 256; i++){
			if(tab[i]) {
				return false;
			}
		}
		return true;
	}

	// deuxieme methode
	public boolean isEmpty() {
		if(size() == 0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * ajoute x à l'ensemble (sans effet si x deja présent)
	 * @param x valeur à ajouter
	 * @pre 0 <= x <= 255
	 */
	public void add(int x) {
		if(x < 0 || x > 256) {
			System.out.println("Ajout impossible");
		}else {
			tab[x] = true;
		}
	}
	
	/**
	 * retire x de l'ensemble (sans effet si x n'est pas présent)
	 */
	public void remove(int x) {
		if(x < 0 || x > 256) {
			System.out.println("Suppression impossible");
		}else {
			tab[x] = false;
		}
	}
	
	/**
	 * ajoute à l'ensemble les valeurs deb, deb+1, deb+2,..., fin
	 * @param begin  debut de l'intervalle
	 * @param end   fin de l'intervalle
	 * @pre  0 <= begin <= end <= 255
	 */
	public void addInterval(int deb, int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb <= fin)) {
			for(int i = deb; i <= fin; i++){
				tab[i] = true;
			}
		}else {
			System.out.println("Revoyez les valeurs de début ou de fin de votre intervalle");
		}
		
	}
	
	/***
	 * retire de l'ensemble les valeurs deb, deb+1, deb+2,..., fin
	 * @param deb debut de l'intervalle
	 * @param fin  fin de l'intervalle
	 * @pre  0 <= begin <= end <= 255
	 */
	public void removeInterval(int deb,int fin) {
		if ((deb >= 0) && (deb < 255) && (fin > 0) && (deb < 256) && (deb <= fin)) {
			for(int i = deb; i <= fin; i++){
				tab[i] = false;
			}
		}else {
			System.out.println("Revoyez les valeurs de début ou de fin de votre intervalle");
		}
	}
	
	/**
	 * realise l'operation this <- this U set2 
	 * @param set2 second ensemble 
	 */
	public void union(SmallSet set2) {
		for(int i = 0; i <= 255; i++){
				if(set2.contains(i)) {
					tab[i] = true; // this.add(i);
			}
		}
	}
	
	
	/**
	 * realise l'operation this<-this intersection set2 
	 * @param set2 second ensemble 
	 */
	public void intersection(SmallSet set2) {
		for(int i = 0; i <= 255; i++){
			if(set2.contains(i) && tab[i]){
				tab[i] = true; // this.add(i);
			}else {
				tab[i] = false; // this.remove(i);
			}
		}
	}
	
	// deuxieme methode
	public void intersection(SmallSet set2) {
		for(int i = 0; i <= 255; i++){
			if(!set2.contains(i)){
				tab[i] = false; // this.add(i);
			}
		}
	}

	/**
	 * realise l'operation this <- this \ set2
	 */
	public void difference(SmallSet set2) {
		for(int i = 0; i <= 255; i++){
			if(set2.contains(i) && tab[i]) {
				tab[i] = false; // this.remove(i);
			}
		}
	}
	
	/**
	 * realise l'operation this <- this delta set2
	 * @param set2
	 */
	// difference de l'intersection et de l'union
	public void symmetricDifference(SmallSet set2) {
		SmallSet t = this.clone();
		this.union(set2); // union
		t.intersection(set2); // intersection
		this.difference(t); // difference
	}
	
	/**
	 * realise l'operation de complémentarite
	 * @param set2
	 */
	public void complement() {
		for(int i = 0; i <= 255; i++){
			if(tab[i]) {
				tab[i]=false;
			}
			else {
				tab[i]=true;
			}
		}
	}
	
	// deuxieme methode
	public void complement() {
		for(int i = 0; i <= 255; i++){
			tab[i] = !tab[i];
		}
	}
	
	/**
	 * realise l'operation de nettoyage
	 */
	public void clear() {
		for (int i = 0; i < 256; i++) {
			tab[i] = false;
		}
	}
	
	/**
	 * @return true, si this ⊆  set2, false sinon
	 */
	public boolean isIncludedIn(SmallSet set2) {
		for(int i = 0; i <= 255; i++){
			if((!set2.contains(i)) && tab[i]) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return copie de this 
	 */
	public SmallSet clone() {
		return new SmallSet(tab);
	}
	
	/**
	 * @return true, si this et set2 sont egaux, false sinon
	 */
	@Override
	public boolean equals(Object set2) {
		if(!(set2 instanceof SmallSet)){
			return false;
		}else {
			SmallSet elm = (SmallSet)set2 ;
			for(int i = 0; i < 256; i++) {
				if(tab[i] != elm.contains(i)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		String s = "éléments présents :";
		for(int i = 0; i <= 255; i++){
			if(tab[i]){
				s = s + i + " ";
			}
		}
		return s;
	}
}
