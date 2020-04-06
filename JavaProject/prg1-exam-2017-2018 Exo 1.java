/*
*  Prog 1 Exam 2017 Decembre
*	Exercice 1
*/

/**
* @param store nom de magasin
* @param id
identifiant de produit
* @return true si un produit d’identifiant id est disponible dans
*
le magasin store , false sinon
*/
public boolean isIdPresent (String store, Integer id){
	// si this contient notre store
	if(this.contains(store){
		// on recupere les valeurs (stock) du store
		stock st = this.getValue(store,);
		// return true si id du produit est present dans stock
		return st.contains(id);
	}
}

public boolean isSold(String store, String name){
	// on recupere les valeurs (stock) du store de this
	Stock st = this.getValue(store);
	// iterator sur le stock pour le parcourir les produits
	Iterator<Integer> it = st.iterator();
	Integer i = 0;
	// on parcout tant que it existe
	while(it.hasnext()){
		//on recupere la valeur de l'id du produit
		i = it.next();
		if(st.getValue(i).name.equals(name)){
			return true;
		}	
	}
	return false;
}

/**
* @param brand1 premiere marque
* @param brand2 seconde marque
* @return la marque entre brand1 et brand2 dont les produits se
*
commercialisent dans le plus grand nombre de magasins de this
*/
public String morePopularBrand(String brand1, String brand2){
	int popBrand1 = 0, popBrand2 = 0;
	// initialisation d'un iterator sur this
	Iterator<String> it = this.iterator();
	// on parcourt this
	while(it.hasnext()){
		// on recupere un stock
		Stock s = this.getValue(it.next());
		// initialisation d'un iterator sur stock
		Iterator<Integer> it2 = s.iterator();
		while(it2.hasnext()){
			// on cree un produit sur le stock
			Integer i = it2.next();
			// si la marque est trouvee
			if(st.getValue(i).brand.equals(brand1)){
				popBrand1++;
			}
			if(st.getValue(i).brand.equals(brand2))){
				popBrand2++;
			}
		}

	} 
	if(popBrand1 > popBrand2) return brand1; else return brand2;
}

/**
* Faire la mise a jour des stocks des magasins a partir de stocks :
* - Si un produit d’un magasin de stocks est présent dans ce magasin
*
dans this, sa quantite est incrementee par la nouvelle quantite.
*
(On suppose que les identifiants sont identiques.)
* - Si un produit d’un magasin de stocks est absent dans ce magasin
*
dans this, il est ajoute dans ce magasin.
* - Si un magasin de stocks est absent de this, il est ajoute avec
*
ses produits.
* @param stocks nouveaux stocks
*/

public void addNewStock(StoreStocks stocks) {
	// initialisation d'un iterator sur stocks
	Iterator<String> it = stocks.iterator();
	// on parcourt stocks
	while(it.hasnext()){
		// on recupere la cle du store de stocks a chaque fois
		String store = it.next();
		// si this ne contient pas ce store
		if(!this.contains(store)){
			// on ajoute a this ce store
			this.addValue(store, stocks.getValue(store));
		}else{
			// on recupere les stocks du store concerne si this ne le contient pas
			Stock st = stocks.getValue(store);
			Stock stThis = this.getValue(store);
			// initialisation d'un iterator sur le stock					
			Iterator<Integer> it = st.iterator();
			// on parcourt les produits du stock
			while(it1.hasnext()){
				// on recupere un produit
				Integer idProd = it1.next();
				// si this ne contient pas ce produit on l'ajoute sinon on incremente sa quandtity
				if(!this.contains(idProd)
					stThis.addValue(idProd, st.getValue(idProd);
				else
					stThis.getValue(idProd).quantity++;
			}
		}
	}
}
			
		
/*
*  Prog 1 Exam 2018 Juin
*  	Exercice 1
*
*/

/**
client du magasin
* @param client
* @param productId identifiant du produit
* @return true si client a achete productId, false sinon
*/
public boolean hasBought(Client client, int productId){
	// si notre client est dans client_base	
	if(this.contains(client)){
		// on recupere les produits du client
		ClientProduct cp = this.getValue(client);
		// initialisation d'un iterator sur les produits
		Iterator<Integer> it = cp.iterator();	
		// on parcourt les produits
		while(it.hasnext()){
			Integer i = it.next();
			// si on retrouve id du produit alors on return true
			if(cp.getValue(i).productID == productID)
				return true;
		}
	}
	return false;
}

/**
* @param productId identifiant du produit
* @return nombre de clients ayant achete le produit productId
*/
public int popularity(int productId){
	// initialisation d'un iterator sur this (ClientBase)
	Iterator<Client> it = this.iterator();
	int count = 0;
	// on parcourt la base de client
	while(it.hasnext()){
		// on recupere un client
		Client client = it.next();
		// on recupere les produits de ce client
		ClientProduct cp = this.getValue(client);
		// initialisation d'un iterator sur les produits
		Iterator<Integer> it1 = cp.iterator();
		// on parcourt les produits du client
		while(it1.hasnext()){
			Integer i = it1.next();
			// si le produit est trouve on incremente count
			if(cp.getValue(i).productID == productID){
				count ++;
			}
		}
	}
	return count;
}

/**
*
* Version deux
*
*/
public int popularity(int productId){
	// initialisation d'un iterator sur this (ClientBase)
	Iterator<Client> it = this.iterator();
	int count = 0;
	// on parcourt la base de client
	while(it.hasnext()){
		// on recupere un client
		Client client = it.next();
		// verifie si le produit est achete
		if(hasBought(client, producID){
			count ++;
		}
	}
	return count;
}

/**
* @return nombre de produits differents achetes par les clients
de this
*
*/
public int productNumber(){
	// initialisation d'un iterator sur this (ClientBase)
	Iterator<Client> it = this.iterator();
	int count = 0;
	// on parcourt this
	while(it.hasnext()){
		// on recupere un client	
		Client client = it.next();
		// on recupere les produits de ce client
		ClientProduct cp = this.getValue(client);
		// initialisation d'un iterator sur les produits
		Iterator<Integer> it1 = cp.iterator();
		// on parcourt les produits
		while(it1.hasnext()){
			// on recupere un produit
			Integer i = it1.next();
			Integer productID = cp.getValue(i).productID;
			// on increment count si c'est ce seul client qui dispose de ce produit
			if(popularity(productID) == 1)
				count ++
		}
	}
	return count;
}

/**
prix minimal
* @param price
* @param quantity quantite minimale
* @return nombre de clients ayant acheté au moins quantity
*
*/
public int clientCount(int price, int quantity){
	// initialisation d'un iterator sur this (ClientBase)
	Iterator<Client> it = this.iterator();
	int countProduct = 0, countClient = 0;
	// on parcourt la base de client
	while(it.hasnext()){
		// on recupere un client
		Client client = it.next();
		// on recupere les produits de ce client
		ClientProduct cp = this.getValue(client);
		// initialisation d'un iterator sur les produits
		Iterator<Integer> it1 = cp.iterator();
		// on parcourt les produits du client
		while(it1.hasnext()){
			Integer i = it1.next();
			// si la quantity et le price du produit sont trouves on incremente countProduct
			if((cp.getValue(i).quantity >= quandtity) && (cp.getValue(i).price > price)){
				countProduct ++;
			}
		}
		// si countProduct > 0 on increment countClient et on remet countProduct a 0
		if(countProduct > 0){ countClient ++; countProduct = 0; }
	}
	return count;
}

/**
* Mettre a jour les quantites des produits achetes suite à des
* retours.
*
* @param returnTable table contenant les produits retournes
* @pre tous les clients de returnTable sont des clients de this
*/
public void returnProducts(Table<Client, Set<Product>> returnTable){
	// initialisation d'un iterator sur this et returnTable
	Iterator<Client> it1 = this.iterator();
	Iterator<Client> it2 = returnTable.iterator();
	// on parcourt this et returnTable
	while(it1.hasnext() && it2.hasnext()){
		// on recupere un client de this et returnTable
		Client client1 = this.getValue(it1.next());
		Client client2 = this.getValue(it2.next());
		// si c'est le même client		
		if(client1.equals(client2)){
			// on recupere les produits
			ClientProduct cp1 = this.getValue(client1);
			ClientProduct cp2 = returnTable.getValue(client2);
			// initialisation d'un iterator sur les produits
			Iterator<Integer> it3 = cp1.getVaue(client1);
			Iterator<Integer> it4 = cp1.getVaue(client2);
			// on parcourt les produits
			while(it3.hasnext() && it4.hasnext()){
				Integer i1 = it3.next():
				Integer i2 = it4.next();
				// on verifie l'ID de chaque produit
				if(cp1.getValue(i1).productID == cp2.getValue(i2).productID)
					// mise a jour de la quantity du produit
					cp1.getValue(i1).quantity = cp1.getValue(i1).quantity - cp2.getValue(i2).quantity:
			}
		}
		
	}
	
}
