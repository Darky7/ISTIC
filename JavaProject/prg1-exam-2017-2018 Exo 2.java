/*
*  Prog 1 Exam 2017 Decembre
*	Exercice 2
*/

/**
* @param countryName nom de pays
* @return true si un pays de nom countryName est present dans this,
*
false sinon
*/
public boolean exists(String countryName){
	// initialisation d'un iterator sur this
	Iterator<Country> it = this.iterator();
	// return resultat de existsAux
	return existsAux(it, countryName);
}

public boolean existsAux(Iterator<Country> it, String countryName){
	// on parcourt tant que notre arbre n'est pas vide
	while(!it.isEmpty()){
		/* 
		* si on retrouve au niveau d'un noeud name on return true sinon on parcourt l'arbre a gauche 			 
		* ensuite a droite
		*/
		if(it.getValue().name.compareTo(countryName) == 0){
			return true;
		}else{
			if(it.getValue().name.compareTo(countryName) < 0){			
				it.Right();
				existsAux(it, countryName);
				it.goUp();
			}else{
				it.Left();
				existsAux(it, countryName);
				it.goUp();
			}
		}
	}
	return false;
}


/**
* Afficher a l’ecran les noms de tous les pays de trouvant a
* profondeur depth dans this, dans l’ordre lexicographique inverse.
*
* @param depth profondeur dans l’arbre
*/
public void printNodeOfDepth(int depth){

}

/**
* @return le pays de this ayant la plus grande superficie.
*/
public Country largestCountry(){
	// initialisation d'un iterator sur this
	Iterator<Country> it = this.iterator();
	// on parcourt tant que notre arbre n'est pas vide
	while(!it.isEmpty()){
		Integer	surface = it.getValue().surface;
		Iterator<Country> myiterator = it;
		it.Left();
		parcours(it, surface);		
	}
	return it;
}

public void parcours(Iterator<Country> it, Integer surface){
	if(it.getValue().surface < surface){
		it.goLeft();
		parcours(it, surface);
		it.goUp();
		it.goRight();		
		parcours(it, surface);
		it.goUp();
	}else{
		Integer	surface = it.getValue().surface;
		Iterator<Country> myiterator = it;
		parcours(it, surface);
	}
}

/**
* @return true si l’arbre est parfait (tous les butoirs se trouvent
* a la meme distance de la racine), false sinon
*/
public boolean isPerfect(){

}


/**
* Ajouter le pays country dans this. Sans effet, si un pays de nom
* country.name est deja present.
* @param country nouveau pays
*/
public void addCountry(Country country){
	Iterator<Country> it = this.iterator();
  addValueAux(it, country); 
}

public void addValueAux(Iterator<Country> it, Country country){
  if(!existsAux(it, country.name)){
		while(!it.isEmpty()){
			if(it.getValue().name.compareTo(countryName) < 0){			
				it.Right();
				addValueAux(it, countryName);
				it.goUp();
			}else{
				it.Left();
				addValueAux(it, countryName);
				it.goUp();
			}      
		}
    it.addValue(country);
	}  
}
