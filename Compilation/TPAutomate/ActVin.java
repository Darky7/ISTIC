import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
* gestion des actions associees a la reconnaissance syntaxique 
* des fiches de livraison de vin
* 
* @author Aguessy, Donou
*/
public class ActVin extends AutoVin {

    /** table des actions */
	public static final int[][] action = {
			/* etat		BJ   BG IDENT NBENT  ,	  ;	   /  AUTRES  */
			/* 0 */	  { 10,  10,  1,   10,  10,   0,   9,  10   },
			/* 1 */	  { 3,   4,   5,	2,  10,   0,  10,  10   },
			/* 2 */	  { 10,  10,  5,   10,  10,   0,  10,  10   },
			/* 3 */	  { 3,   4,   5,   10,  10,   0,  10,  10   },
			/* 4 */	  { 10,  10,  10,   6,  10,   0,  10,  10   },
			/* 5 */	  { 10,  10,  5,   10,   7,   8,  10,  10   },
			/* 6 */	  { 11,  11,  11,  11,  11,   0,  11,  11   }
	} ;      
    	 
    /** constructeur classe ActVin */
    public ActVin(InputStream flot) {
        super(flot);
    }
 
    /** types d'erreurs detectees */
    private static final int FATALE = 0, NONFATALE = 1, INITVOLUME = 100;
 
    /** taille d'une colonne pour affichage ecran */
    private static final int MAXLGID = 20;
    
    /** nombre maximum de chauffeurs */
    private static final int MAXCHAUF = 10;
    
    /** tableau des chauffeurs et resume des livraison de chacun */
    private Chauffeur[] tabChauf = new Chauffeur[MAXCHAUF];
   
    /** indice courant du nombre de chauffeurs dans le tableau tabChauf */
    private int ichauf = 0,  bj = 0, bg = 0, ordin = 0, volume = 0, currentChauf = 0, quantiteLivree = 0;
   
    private static String[] qualite = {"ORDINAIRE", "BEAUJOLAIS", "BOURGOGNE"};
    private String typeQualite = "ORDINAIRE";
 
    /** variables de comptes */
    private static int bj_total = 0, bg_total = 0, ordin_total = 0;
   
    /** SmallSet des magasins */
    private static SmallSet magdif = new SmallSet();
   
    /** utilitaire d'affichage a l'ecran
     * @param ch est une chaine de longueur quelconque
     * @return chaine ch cadree a gauche sur MAXLGID caracteres
     * */
    private String chaineCadrageGauche(String ch) {
        int lgch = Math.min(MAXLGID, ch.length());
        String chres = ch.substring(0, lgch);
        for (int k = lgch; k < MAXLGID; k++)
            chres = chres + " ";
        return chres;
    }
 
    /** affichage de tout le tableau de chauffeurs a l'ecran */
    private void afficherchauf() {
        String idchaufcour, ch;
        Ecriture.ecrireStringln("");
        ch = "CHAUFFEUR                   BJ        BG       ORD     NBMAG\n"
                + "---------                   --        --       ---     -----";
        Ecriture.ecrireStringln(ch);
        for (int i = 0; i < ichauf; i++) {
            //System.out.println(" numchauf courant "+tabChauf[i].numchauf);
            idchaufcour = ((LexVin)lex).repId(tabChauf[i].numchauf);
            Ecriture.ecrireString(chaineCadrageGauche(idchaufcour));
            Ecriture.ecrireInt(tabChauf[i].bj, 10);
            Ecriture.ecrireInt(tabChauf[i].bg, 10);
            Ecriture.ecrireInt(tabChauf[i].ordin, 10);
            Ecriture.ecrireInt(tabChauf[i].magdif.size(), 10);
            Ecriture.ecrireStringln("");
        }
    }
   
    /** gestion des erreurs
     * @param te type de l'erreur
     * @param messErr message associe a l'erreur
     */
    private void erreur(int te, String messErr) {
        Lecture.attenteSurLecture(messErr);
        switch (te) {
        case FATALE:
            errFatale = true;
            break;
        case NONFATALE:
            etatCourant = etatErreur;
            break;
        default:
            Lecture.attenteSurLecture("parametre incorrect pour erreur");
        }
    }
 
    /**
     * initialisations a effectuer avant les actions
     */
    private void initialisations() {
        ichauf = 0;
        /*!!! A COMPLETER SI BESOIN !!!*/
    }
   
    /**
     * acces a un attribut lexical
     * cast pour preciser que lex est de type LexVin
     * @return valNb associe a l'unite lexicale nbentier
     */
    private int valNb() {
        return ((LexVin)lex).getValNb();
    }
    /**
     * acces a un attribut lexical
     * cast pour preciser que lex est de type LexVin
     * @return numId associe a l'unite lexicale ident
     */
    private int numId() {
        return ((LexVin)lex).getNumId();
    }
   
    private int findChauf(Chauffeur[] tabChauf, int currentchauf) {
        int i = 0;
        while(i < ichauf) {
            if(tabChauf[i].numchauf == currentchauf) return i;
            i++;
        }
        return -1;
    }
 
    /**
     * execution d'une action
     * @param numact numero de l'action a executer
     */
    public void executer(int numact) {
    	switch (numact) {
       
        	case -1: break;
        	
        	case 0: initialisations();
       
        	// lecture ident nom chauffeur
        	case 1: currentChauf = numId();
        			if((findChauf(tabChauf, currentChauf) == -1) && ((ichauf + 1) == MAXCHAUF))
        				erreur(FATALE, "Nombre maximum de chauffeurs atteint");
        			break;
 
        	// lecture volume citerne
        	case 2: volume = valNb();
                	if ((volume < 100) || (volume > 200))
                		volume = INITVOLUME;
                	break;
       
            // lecture de BEAUJOLAIS
        	case 3: typeQualite = qualite[1];
        			break;
       
        	// lecture de BOURGOGNE
        	case 4: typeQualite = qualite[2];
        			break;
               
        	// lecture ident nom du magasin et ajout si absent
        	case 5: if (!magdif.contains(numId()))
                    	magdif.add(numId()); 
                	break;
 
            // quantite livree dans le magasin
        	case 6: quantiteLivree = valNb();
        			if (quantiteLivree <= 0)
        				erreur(NONFATALE, "Quantite livree incorrecte");
        			switch (typeQualite) {
               
                    	case "ORDINAIRE":
                    			ordin += quantiteLivree;
                    			break;
 
                    	case "BEAUJOLAIS":
                    			bj += quantiteLivree;
                    			break;
 
                    	case "BOURGOGNE":
                    			bg += quantiteLivree;
                    			break;
        			}
        			break;
               
        	// lecture de ','
        	case 7: if ((bj + bg + ordin) > volume)
        				erreur(NONFATALE, "Quantite livree superieure a la capacite de la citerne");
                	switch (typeQualite) {
                    	
                		case "ORDINAIRE":
                    	     	ordin_total += ordin;
                    			ordin = 0;
                    			break;
 
                    	case "BEAUJOLAIS":
                    			bj_total += bj;
                    			bj = 0;
                    			break;
                    	
                    	case "BOURGOGNE":
                    			bg_total += bg;
                    			bg = 0;
                    			break;
                	}
                	typeQualite = "ORDINAIRE";
                	break;
       
            // lecture de ';'
        	case 8: switch (typeQualite) {
        				
        				case "ORDINAIRE":
        						ordin_total += ordin;
        						ordin = 0;
        						break;
 
        				case "BEAUJOLAIS":
        						bj_total += bj;
        						bj = 0;
        						break;
                    
        				case "BOURGOGNE":
        						bg_total += bg;
        						bg = 0;
        						break;
                	}                              
                	if (findChauf(tabChauf, currentChauf) == -1) {
                		tabChauf[ichauf] = new Chauffeur(currentChauf, bj_total, bg_total, ordin_total, magdif.clone());
                		ichauf++;                   
                	} else {
                		Chauffeur current = tabChauf[findChauf(tabChauf, currentChauf)];
                		current.bg += bg_total;
                		current.bj += bj_total;
                		current.ordin += ordin_total;
                		current.magdif.union(magdif);                   
                	}
                	typeQualite = "ORDINAIRE"; volume = 0;
                	bj_total = 0; bg_total = 0; ordin_total = 0;
                	magdif.clear();
               
                	afficherchauf();
                	break;
           
            // lecture de '/'
        	case 9: int max = -1, meilleurChauf = 0, i = 0;
        			while (i < ichauf) {
        				Chauffeur current = tabChauf[i];
        				if (current.magdif.size() > max) {
        					max = current.magdif.size();
        					meilleurChauf = current.numchauf;
        				}
        				i++;
        			}
        			System.out.println("\n Le chauffeur ayant livre le plus de magasins differents ce mois-ci est : " + ((LexVin)lex).repId(meilleurChauf));
        			break;
       
        	// Action erreur
        	case 10: erreur(NONFATALE, "Syntaxe invalide");
        			 break;
        			 
        	case 11: break;
               
        	default: Lecture.attenteSurLecture("action " + numact + " non prevue");
        }
    }
   
    /**
     * definition methode abstraite faireAction de Automate
     */
     public void faireAction(int etat, int unite) {
         executer(action[etat][unite]);
     };
     
     /**
      * definition methode abstraite initAction de Automate
      */
     public void initAction() {
        // action 0 a effectuer a l'init
         initialisations();
     };
 
     /**
      * definition methode abstraite getAction de Automate
      */
     public int getAction(int etat, int unite) {
        // return -1;
        return action[etat][unite];
     };
 
 
} // class Actvin