/*
 *PRG1 - TP1 - Codage et etude guidee d'un programme
 *
 * AGUESSY Dodji Onésime
 * DONOU Dario
 * 
 */


public class TriBoule {
	static int nombreBoules = 0;	
	
	public static  char[] lireTableauBoules() {
		// resultat : tableau contenant la suite des caractères lus	
		char contenu;
		int a=0;
		char tab[] = new char[nombreBoules];
		while(a!=nombreBoules){
			contenu=Lecture.lireChar();
			if(contenu=='r' || contenu=='b'|| contenu=='v'){
				tab[a] = contenu;
				a++;
			}
		}			
		return tab;
	}
	
	
	public static void ecrireTableauBoules(char[] tab) {
		// effet : le contenu de tab est affiche a l'ecran
		for(int i = 0; i < nombreBoules; ++i) {
			Ecriture.ecrireChar(tab[i]);
		}
	}
	
	public static void echange(int i, int j, char[] tab) {
	    // effet : echange tab[i] et tab[j]
		char c = tab[i];
		tab[i] = tab[j];
		tab[j] = c;
	}
	
	public static void photo(int r, int s, int t, char[] tab) {
		// effet : afficher les valeurs intermediaires des indices r, s, t et tab
		Ecriture.ecrireStringln("r = "+ r +" s = "+ s +" t = "+ t);
		ecrireTableauBoules(tab);
		Ecriture.ecrireStringln("");
	}


	public static void main(String[] args){
		char[] tableauBoules;
		
		Ecriture.ecrireString("entrer le nombre de boule a ecrire :");
		
		nombreBoules = Lecture.lireInt();
	
		Ecriture.ecrireString("suite des "+ nombreBoules +" boules : ");
		
		tableauBoules = lireTableauBoules();
		
		int r = 0, s = 0, t = nombreBoules -1;
		
		while( s <= t ) {
			
			photo(r, s, t, tableauBoules);
			
			switch (tableauBoules[s]) {
					case 'v' : echange(r, s, tableauBoules);
							   ++r;
							   ++s;
							   break;
							   
					case 'b' : ++s;
							   break;
							   
					case 'r' : echange(s, t, tableauBoules);
							   --t;
							   break;
							   
					default : System.out.println("erreur : s = "+ s +", boule = "+ tableauBoules[s]);
							  
					System.exit(0);
			}
							
		}
		
		Ecriture.ecrireString("résultat du tri : ");
		ecrireTableauBoules(tableauBoules);
		Ecriture.ecrireStringln("");
		
		
		
	}
	
}