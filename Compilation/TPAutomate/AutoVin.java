import java.io.*;

/**
 * automate de reconnaissance syntaxique des fiches de livraison de vin
 * 
 * @author Aguessy, Donou
 *
 */
public class AutoVin extends Automate{

	/** table des transitions */
	public static final int[][] transit = {
			/* etat	    BJ   BG IDENT NBENT  ,	  ;	   /  AUTRES  */
			/* 0 */	  { 6,   6,   1,	6,   6,   0,   7,   6   },
			/* 1 */	  { 2,   2,   4,	3,   6,   0,   6,   6   },
			/* 2 */	  { 6,   6,   4,	6,   6,   0,   6,   6   },
			/* 3 */	  { 2,   2,   4,	6,   6,   0,   6,   6   },
			/* 4 */	  { 6,   6,   6,	5,   6,   0,   6,   6   },
			/* 5 */	  { 6,   6,   4,	6,   3,   0,   6,   6   },
			/* 6 */	  { 6,   6,   6,	6,   6,   0,   6,   6   }
	};
	
	/** utilitaire de suivi des modifications pour affichage */
	public void newObserver(ObserverAutomate oAuto, ObserverLexique oLex ){
		this.newObserver(oAuto);
		this.lex.newObserver(oLex);
		lex.notifyObservers(((LexVin)lex).getCarlu());
	}
 
	/** constructeur classe AutoVin */
	public AutoVin(InputStream flot) {
		/** on utilise ici un analyseur lexical de type LexVin */
		lex = new LexVin(flot);
		this.etatInitial = 0;
		this.etatFinal = transit.length;
		this.etatErreur = transit.length - 1;
	}

	/** definition de la methode abstraite getTransition de Automate */
	int getTransition(int etat, int unite) {
		return this.transit[etat][unite];
	}

	/** ici la methode abstraite faireAction de Automate n'est pas encore definie */
	void faireAction(int etat, int unite) {};
	
	/** ici la methode abstraite actionInit de Automate n'est pas encore definie */
	void initAction() {};
	
	/** ici la methode abstraite getAction de Automate n'est pas encore definie */
	int getAction(int etat, int unite) {return 0;};
		
}/** AutoVin */
