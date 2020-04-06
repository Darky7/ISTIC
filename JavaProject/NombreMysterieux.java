/*
 * TP2 : Exercice 2
 * AGUESSY Onésime
 * DONOU Dario 
 */
import java.util.Scanner;
public class NombreMysterieux {
	private static Scanner sc;

	/* fonction de verification de n*n et n*n*n*
	* initialisation d'un tableau tab[] avec 10 valeurs à 0
	* initialisation d'un tableau tab[2] avec caractères allant de 0 à 9
	* on parcourt la valeur du premier nombre devenu une chaîne de caractères
	* en comptant le nombre d'occurence de chaque caractère. 
	* le resultat est renseigné pour chaque caractère dans le tableau initialisé auparavant à 0.
	* ensuite ce tableau est utilisé pour vérifier le nombre d'occurence de
	* chaque caractère pour la deuxieme chaîne (n*n*n) et le tableau est
	* a nouveau rempli avec les nouvelles valeurs.
	* ensuite on procède à une vérification dans le tableau tab[] si une colonne comporte les valeurs
	* 0 ou 2 (c'est à dire soit le chiffre n'apparait pas ou soit il apparait plus d'une fois
	* alors ce n'est pas un nombre mystérieux). */
	public static boolean Calcul(String nombre1, String nombre2) {
		int tab[] = {0,0,0,0,0,0,0,0,0,0};
		char tab2[] = {'0','1','2','3','4','5','6','7','8','9'};
		// Verification pour le nombre1 (n*n)
		for(int i = 0; i < nombre1.length(); ++i) {
			for(int j = 0; j < tab2.length; ++j) {
				if(nombre1.charAt(i) == tab2[j]) {
					tab[j] ++;
				}
			}	
		}
		// Verification pour le nombre2 (n*n*n)
		for(int i = 0; i < nombre2.length(); ++i) {
			for(int j = 0; j < tab2.length; ++j) {
				if(nombre2.charAt(i) == tab2[j]) {
					tab[j] ++;
				}
			}	
		}
		int count = 0;
		for(int t = 0; t<tab.length; ++t) {
			if(tab[t] == 0 || tab[t] == 2) {
				count ++;
			}
		}
		if(count > 0) return false; else return true;
	}
	
	public static void main(String[] args) {
		int nombre; 
		String nomNombre1, nomNombre2;
		
		sc = new Scanner(System.in);	
		System.out.println("Entrer un nombre positif : ");
		
		nombre = sc.nextInt();
		
		while(nombre < 0) {
			System.out.println("Entrer un nombre positif : ");
			nombre = sc.nextInt();
		}
		
		// on ecrit notre nombre (n*n) et (n*n*n) sous la forme chaine de caractere
		nomNombre1 = String.valueOf(nombre*nombre);
		nomNombre2 = String.valueOf(nombre*nombre*nombre);
		
		if(Calcul(nomNombre1, nomNombre2)) {
			System.out.println("Le nombre est un nombre mystérieux");
		}else {
			System.out.println("Le nombre n'est pas un nombre mystérieux");
		}	
		
	}
}
