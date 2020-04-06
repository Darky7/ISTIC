/*
 * TP2 : Exercice 1
 * AGUESSY Onésime
 * DONOU Dario 
 */
import java.util.Scanner;
public class TriDichotomique {
	private static Scanner sc;
	
	/* cette fonction permet de trier notre tableau T*/
	public static void TriNaif(int T[]) {
		for(int i = 0; i<= T.length-2; ++i) {
			int rangmin = i;
			for(int j = i+1; j <= T.length-1; ++j) {
				if(T[j] < T[rangmin]) {
				rangmin = j;
				}
			}
				int aux = T[i];
				T[i] = T[rangmin];
				T[rangmin] = aux;
		}
	}
	
	/* cette fonction permet de verifier si a est dans le tableau T */
	public static boolean SearchDic(int T[], int a) {
		int deb = 0, fin = T.length-1, milieu = (deb + fin) /2;
		while(deb <= fin && a != T[milieu]) {
			if(a < T[milieu]) {
				fin = milieu - 1;
			}
			if(a > T[milieu]) {
				deb = milieu + 1;
			}
			milieu = (deb + fin) / 2;
		}
		
		return deb <= fin;
	}
	
	public static void main(String[] args) {
		int valeur, tailleDuTableau;
		
		System.out.println("Entrer la taille de votre tableau : ");
		
		sc = new Scanner(System.in);
		
		tailleDuTableau = sc.nextInt();
		
		int T[] = new int[tailleDuTableau];
		
		if(1 <= tailleDuTableau && tailleDuTableau <= 50) {
			System.out.println("Entrer les valeurs du tableau : ");
			for(int i = 0; i < tailleDuTableau; ++i) {
				valeur = sc.nextInt();
				T[i] = valeur;				
			}
		
			System.out.println("Le tableau trié : ");
			TriNaif(T);
		
			for(int i = 0; i < T.length; ++i) {
				System.out.println(T[i]);
			}
		
			System.out.println("Entrer le nombre a rechercher : ");
		
			valeur = sc.nextInt();
		
			if(SearchDic(T, valeur)) {
				System.out.println("Le nombre est dans notre tableau");
			}else {
				System.out.println("Le nombre n'est pas dans notre tableau");
			}
		
		}else {
			System.out.println("Verifier la taille du tableau");
		}	
		
	}

}
