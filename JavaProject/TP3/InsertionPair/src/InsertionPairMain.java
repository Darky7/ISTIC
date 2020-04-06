/*
 * TP3 : Exercice 3
 * AGUESSY Onésime
 * DONOU Dario 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InsertionPairMain {
	private static Scanner scanner, s1, s2;
	public static void main (String[] args) throws FileNotFoundException {	   
			scanner = new Scanner(System.in);
			
			// creation du tableau d'objets Pair
			InsertionPair monInsertion = new InsertionPair();
			
			// insertion des elements dans notre tableau
			monInsertion.createArray(scanner);
			
			/* le fichier dont on veut lire le contenu se nomme nombre.txt
			donc veuillez ecrire nombre pour que cela puisse marcher*/
			System.out.println("Veuillez entrer le nom du fichier à lire:");
			s1 = new Scanner(System.in);
			String nom = s1.nextLine();
			
			
			// le chemin d'acces au fichier doit etre modifier suivant chaque fichier
			File f1 = new File("N:\\l3miage\\prg1\\tp3\\" + nom + ".txt");
			s2 = new Scanner(f1);
			monInsertion.createArray(s2);
			scanner.close();
			s2.close();
			
			
	}

}
