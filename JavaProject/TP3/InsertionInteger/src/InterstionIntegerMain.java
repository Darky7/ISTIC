/*
 * TP3 : Exercice 2
 * AGUESSY Onésime
 * DONOU Dario 
 */
import java.util.Scanner;

public class InterstionIntegerMain {
	public static void main (String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		// creation du tableau 
		InsertionInteger monInsertion = new InsertionInteger();
		
		// insertion des elements dans notre tableau
		monInsertion.createArray(scanner);
		
		// affichage de toString
		System.out.println(monInsertion.toString());
		
	}

}
