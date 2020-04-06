/*********************************************************************************
 * VARIABLES ET METHODES FOURNIES PAR LA CLASSE UtilLex (cf libclass)            *
 *       complement à l'ANALYSEUR LEXICAL produit par ANTLR                      *
 *                                                                               *
 *                                                                               *
 *   nom du programme compile, sans suffixe : String UtilLex.nomSource           *
 *   ------------------------                                                    *
 *                                                                               *
 *   attributs lexicaux (selon items figurant dans la grammaire):                *
 *   ------------------                                                          *
 *     int UtilLex.valNb = valeur du dernier nombre entier lu (item nbentier)    *
 *     int UtilLex.numId = code du dernier identificateur lu (item ident)        *
 *                                                                               *
 *                                                                               *
 *   methodes utiles :                                                           *
 *   ---------------                                                             *
 *     void UtilLex.messErr(String m)  affichage de m et arret compilation       *
 *     String UtilLex.repId(int nId) delivre l'ident de codage nId               *
 *     void afftabSymb()  affiche la table des symboles                          *
 *********************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

// classe de mise en oeuvre du compilateur
// =======================================
// (verifications semantiques + production du code objet)

public class PtGen {
    

    // constantes manipulees par le compilateur
    // ----------------------------------------

	private static final int 
	
	// taille max de la table des symboles
	MAXSYMB=300,

	// codes MAPILE :
	RESERVER=1,EMPILER=2,CONTENUG=3,AFFECTERG=4,OU=5,ET=6,NON=7,INF=8,
	INFEG=9,SUP=10,SUPEG=11,EG=12,DIFF=13,ADD=14,SOUS=15,MUL=16,DIV=17,
	BSIFAUX=18,BINCOND=19,LIRENT=20,LIREBOOL=21,ECRENT=22,ECRBOOL=23,
	ARRET=24,EMPILERADG=25,EMPILERADL=26,CONTENUL=27,AFFECTERL=28,
	APPEL=29,RETOUR=30,

	// codes des valeurs vrai/faux
	VRAI=1, FAUX=0,

    // types permis :
	ENT=1,BOOL=2,NEUTRE=3,

	// categories possibles des identificateurs :
	CONSTANTE=1,VARGLOBALE=2,VARLOCALE=3,PARAMFIXE=4,PARAMMOD=5,PROC=6,
	DEF=7,REF=8,PRIVEE=9,

    //valeurs possible du vecteur de translation 
    TRANSDON=1,TRANSCODE=2,REFEXT=3;

	private static int code, indexVar = 0, identAffect, varType, varCat, varInfo, nbParam, paramInTabSymb = -1, varConstanteInProc = 0;
	private static List<Integer> suiteBincond = new ArrayList<Integer>();

    // utilitaires de controle de type
    // -------------------------------
    
	private static void verifEnt() {
		if (tCour != ENT)
			UtilLex.messErr("expression entiere attendue");
	}

	private static void verifBool() {
		if (tCour != BOOL)
			UtilLex.messErr("expression booleenne attendue");
	}

    // pile pour gerer les chaines de reprise et les branchements en avant
    // -------------------------------------------------------------------

    private static TPileRep pileRep;  


    // production du code objet en memoire
    // -----------------------------------

    private static ProgObjet po;
    
    
    // COMPILATION SEPAREE 
    // -------------------
    //
    // modification du vecteur de translation associe au code produit 
    // + incrementation attribut nbTransExt du descripteur
    // NB: effectue uniquement si c'est une reference externe ou si on compile un module
    private static void modifVecteurTrans(int valeur) {
		if (valeur == REFEXT || desc.getUnite().equals("module")) {
			po.vecteurTrans(valeur);
			desc.incrNbTansExt();
		}
	}
    
    // descripteur associe a un programme objet
    private static Descripteur desc;

     
    // autres variables fournies
    // -------------------------
    public static String trinome = "AGUESSY_DONOU_DOSSOU"; // MERCI de renseigner ici un nom pour le trinome, constitue de exclusivement de lettres
    
    private static int tCour; // type de l'expression compilee
    private static int vCour; // valeur de l'expression compilee le cas echeant
  
   
    // D�finition de la table des symboles
    //
    private static EltTabSymb[] tabSymb = new EltTabSymb[MAXSYMB + 1];
    
    // it = indice de remplissage de tabSymb
    // bc = bloc courant (=1 si le bloc courant est le programme principal)
	private static int it, bc;
	
	// utilitaire de recherche de l'ident courant (ayant pour code UtilLex.numId) dans tabSymb
	// rend en resultat l'indice de cet ident dans tabSymb (O si absence)
	private static int presentIdent(int binf) {
		int i = it;
		while (i >= binf && tabSymb[i].code != UtilLex.numId)
			i--;
		if (i >= binf)
			return i;
		else
			return 0;
	}

	// utilitaire de placement des caracteristiques d'un nouvel ident dans tabSymb
	//
	private static void placeIdent(int c, int cat, int t, int v) {
		if (it == MAXSYMB)
			UtilLex.messErr("debordement de la table des symboles");
		it = it + 1;
		tabSymb[it] = new EltTabSymb(c, cat, t, v);
	}

	// utilitaire d'affichage de la table des symboles
	//
	private static void afftabSymb() { 
		System.out.println("       code           categorie      type    info");
		System.out.println("      |--------------|--------------|-------|----");
		for (int i = 1; i <= it; i++) {
			if (i == bc) {
				System.out.print("bc=");
				Ecriture.ecrireInt(i, 3);
			} else if (i == it) {
				System.out.print("it=");
				Ecriture.ecrireInt(i, 3);
			} else
				Ecriture.ecrireInt(i, 6);
			if (tabSymb[i] == null)
				System.out.println(" reference NULL");
			else
				System.out.println(" " + tabSymb[i]);
		}
		System.out.println();
	}
    

	// initialisations A COMPLETER SI BESOIN
	// -------------------------------------

	public static void initialisations() {
	
		// indices de gestion de la table des symboles
		it = 0;
		bc = 1;
		
		// pile des reprises pour compilation des branchements en avant
		pileRep = new TPileRep(); 
		// programme objet = code Mapile de l'unite en cours de compilation
		po = new ProgObjet();
		// COMPILATION SEPAREE: desripteur de l'unite en cours de compilation
		desc = new Descripteur();
		
		// initialisation necessaire aux attributs lexicaux
		UtilLex.initialisation();
	
		// initialisation du type de l'expression courante
		tCour = NEUTRE;

	} // initialisations

	// code des points de generation A COMPLETER
	// -----------------------------------------
	public static void pt(int numGen) {
	
		switch (numGen) {
			case 0: // initialisation
					initialisations();
					break;
			
			// lecture d'un ident
			case 1: code = UtilLex.numId; 
					break;
			
			// lecture d'une constante
			case 2: if(bc == 1) {
						if (presentIdent(bc)== 0){ placeIdent(code, CONSTANTE, tCour, vCour);}
						else UtilLex.messErr("Constante " + UtilLex.repId(code) + " existe deja.");
					}else {
						if (presentIdent(bc)== 0){ placeIdent(code, CONSTANTE, tCour, vCour);}
						else UtilLex.messErr("Constante " + UtilLex.repId(code) + " existe deja.");
						varConstanteInProc++;
					}
					break;										
			
			// lecture du type ENT
			case 3: tCour = ENT;
					break;
					
			// lecture du type BOOL
			case 4: tCour = BOOL;
					break;
					
			// lecture d'un ident
			case 5:	if (bc == 1) {
						// declaration d'une variable globale				
						if(presentIdent(bc) == 0) { placeIdent(UtilLex.numId, VARGLOBALE, tCour, indexVar); indexVar++; }
						else UtilLex.messErr("Variable " + UtilLex.repId(code) + " existe deja.");	
						break;
					}else {
						// bc > 1 declaration d'une variable locale
						if(presentIdent(bc) == 0) { paramInTabSymb++; placeIdent(UtilLex.numId, VARLOCALE, tCour, paramInTabSymb); indexVar++; }
						else UtilLex.messErr("Variable " + UtilLex.repId(code) + " existe deja.");	
						break;
					}
					
			// fin de lecture des differentes variables :: on produit du code MAPILE
			case 6: po.produire(RESERVER);
					po.produire(indexVar);
					break;
			
			// lecture d'une expression boolean
			case 11: verifBool();
					 break;
			
			// fin de lecture d'une expression boolean "OU"
			case 12: po.produire(OU);
					 break;
			
			// fin de lecture d'une expression boolean "ET"
			case 13: po.produire(ET);
					 break;

			// fin de lecture d'une expression boolean "NON"	
			case 14: po.produire(NON);
					 break;
			
			// lecture d'un entier
			case 15: verifEnt();
					 break;
					 
			// fin de lecture d'une expression entiere avec "="
			case 16: po.produire(EG);
					 tCour=BOOL;
					 break;
					 
			// fin de lecture d'une expression entiere avec "<>"
			case 17: po.produire(DIFF);
					 tCour=BOOL;
					 break;

			// fin de lecture d'une expression entiere avec ">"
			case 18: po.produire(SUP);
					 tCour=BOOL;
					 break;
					 
			// fin de lecture d'une expression entiere avec ">="
			case 19: po.produire(SUPEG);
					 tCour=BOOL;
					 break;
					 
			// fin de lecture d'une expression entiere avec "<"
			case 20: po.produire(INF);
					 tCour=BOOL;
					 break;
			
			// fin de lecture d'une expression entiere avec "<="
			case 21: po.produire(INFEG);
					 tCour=BOOL;
				     break;
		
		    // lecture d'une expression entiere avec addition "+" 
			case 22: po.produire(ADD);
				     break;
			
		    // lecture d'une expression entiere avec soustraction "-"
			case 23: po.produire(SOUS);
				     break;
			
			// lecture d'une expression entiere avec multiplication "*"
			case 24: po.produire(MUL);
					 break;
				
			// lecture d'une expression entiere avec division "div"
			case 25: po.produire(DIV);
					 break;
			
			// lecture d'une valeur
			case 26: po.produire(EMPILER);
					 po.produire(vCour);
					 break;
					
			// lecture d'un nbentier ou "+" nbentier
			case 27: tCour = ENT; vCour = UtilLex.valNb;
					 break;

			// lecture d'un "-" nbentier
			case 28: tCour = ENT; vCour = - UtilLex.valNb;
					 break;
			
		    // lecture de "vrai"
			case 29: tCour = BOOL; vCour = 1;
					 break;
					 
			// lecture de "faux"
			case 30: tCour = BOOL; vCour = 0;
					 break;
			
			// code MAPILE :: evaluation d'un ident 
			case 31: // on verifie si notre ident est present dans tabSymb 
					if(presentIdent(1)== 0) UtilLex.messErr("L'identifiant " +  UtilLex.repId(UtilLex.numId)+ " n'existe pas dans tabSymb");
					    // code MAPILE a applique suivant la categorie de notre ident
					 	switch (tabSymb[presentIdent(1)].categorie) {
					 	    // cas d'une constante
					 		case CONSTANTE: // on recupere la categorie et le type
							 				tCour = tabSymb[presentIdent(1)].type;
							 				po.produire(EMPILER);
					 						po.produire(tabSymb[presentIdent(1)].info);
					 						break;
					 		// case d'une variable globale
					 		case VARGLOBALE: // on recupere la categorie et le type
							 				 tCour = tabSymb[presentIdent(1)].type;
							 				 po.produire(CONTENUG);
							 				 po.produire(tabSymb[presentIdent(1)].info);
					 						 break;
					 					
					 		//cas d'une variable locale
                            case VARLOCALE: tCour = tabSymb[presentIdent(1)].type;
                                 			po.produire(CONTENUL);
                                 			po.produire(tabSymb[presentIdent(1)].info);
                                 			po.produire(0);
                                 			break;
                            
                            //cas d'un parametre fixe
                            case PARAMFIXE: tCour = tabSymb[presentIdent(1)].type;
                                 		   	po.produire(CONTENUL);
                                 		   	po.produire(tabSymb[presentIdent(1)].info);
                                 		   	po.produire(0);
                                 		   	break;
                            
                            //cas d'un paramètre modifiable
                            case PARAMMOD: tCour = tabSymb[presentIdent(1)].type;
                                 		   po.produire(CONTENUL);
                                 		   po.produire(tabSymb[presentIdent(1)].info);
                                 		   po.produire(1);
                                 		   break;  
					 			
					 	    default: UtilLex.messErr("Erreur sur la categorie " +tabSymb[presentIdent(1)].categorie);
					 	    		 break;
					 	}
					 	break;
		
			/**
			 *  Case 32 / 33 / 57 sont lies
			 */
					 	
			// lecture de ident avant affectation
			case 32: // on verifie su notre ident est present dans tabSymb
					 identAffect = presentIdent(1);
					 if(identAffect == 0) UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'existe pas dans tabSymb");
					 	// on verifie si le type de la categorie :: si CONSTANTE erreur
					 	if(tabSymb[identAffect].categorie == CONSTANTE  || tabSymb[identAffect].categorie == PARAMFIXE)
					 		UtilLex.messErr("Affection impossible, nous sommes en presence d'une constante");
					 	// on recupere la categorie et le type					 	
					 	tCour = tabSymb[identAffect].type;
					 	varType = tabSymb[identAffect].type;
					 	varCat = tabSymb[identAffect].categorie;
					 	varInfo = tabSymb[identAffect].info; 
					 break;
					 
			// affectation ":="
			case 33: if(varType == ENT) verifEnt();
					 else verifBool();
					 // code MAPILE affectation
            		 switch(varCat){
            		 	// variable globale
            		 	case VARGLOBALE: po.produire(AFFECTERG);
            							 po.produire(varInfo);
            							 break;

            			// variable locale
            			case VARLOCALE: po.produire(AFFECTERL);
            							po.produire(varInfo);
            							po.produire(0);
            							break;
            						
            			// parametre modifiable
            			case PARAMMOD: po.produire(AFFECTERL);
            						   po.produire(varInfo);
            						   po.produire(1);
            						   break;
            						 
            			default: UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'est pas affectable.");
            					 break;
            		}      
            		break;
	 				
	 		// lecture d'un ident
			case 34: // on verifie si notre ident est present dans tabSymb 
					 int index1 = presentIdent(bc);
					 if(index1== 0) UtilLex.messErr("L'identifiant " + UtilLex.repId(code) + " n'existe pas dans tabSymb");
					 	// on verifie si le type de la categorie :: si CONSTANTE erreur
		 				if(tabSymb[index1].categorie == CONSTANTE || tabSymb[index1].categorie == PARAMFIXE)
		 					UtilLex.messErr("Affectation impossible, nous sommes en presence d'une constante");
		 				else {
		 					// on verifie le type
		 					if( tabSymb[index1].type == ENT) {
		 						po.produire(LIRENT);
		 					}else {
		 						po.produire(LIREBOOL);
		 					}		 						
		 					switch(tabSymb[index1].categorie){						 		  	
		 						case VARGLOBALE: po.produire(AFFECTERG);
		 										 po.produire(tabSymb[index1].info);
								  				 break;
				 		  	
								case VARLOCALE: po.produire(AFFECTERL);
				 		  						po.produire(tabSymb[index1].info);
				 		  						po.produire(0);
				 		  						break;
								  	
								case PARAMMOD: po.produire(AFFECTERL);
											   po.produire(tabSymb[index1].info);
											   po.produire(1);
											   break;
							      		   
							    default: UtilLex.messErr("Erreur sur le type " +tabSymb[index1].type);
							    		 break;
		 					}
		 			}
		 			break;
		 			
			// ecriture d'un ident
			case 35: 	//afftabSymb();
				 	 	switch (tCour) {
				 	 		// cas d'un entier
				 	 		case ENT: po.produire(ECRENT);
				 	 				  break;
				 	 				  
				 	 		// cas d'un boolean  
				 	 		case BOOL: po.produire(ECRBOOL);
				 	 				   break;
				 	 				   
				 	 		default: UtilLex.messErr("Erreur sur le type " +tCour);
				 	 				 break;
				 	 	}
				 	 break;
				 	
			// expression d'un SI
			case 36: // on verifie si l'expression est un boolean
					 verifBool();
					 // on produit le code MAPILE BSIFAUX
					 po.produire(BSIFAUX);
					 // on reserve un emplacement dans la pile d'instruction
					 po.produire(0);
					 // empiler l'adresse de la valeur du saut dans la pile des reprises, elle sera completee
					 // lorsque le numero de ligne ou effectuer le branchement sera connue
					 pileRep.empiler(po.getIpo());
					 break;

			// cas d'un SINON au cours d'un SI
			case 37: // on produit le code MAPILE BINCOND
				 	 po.produire(BINCOND);
				     // on reserve l'emplacement dans la pile d'instruction
				 	 po.produire(0);
				 	 // on depile l'indice du BSIFAUX (ipo)
				 	 int indice_si = pileRep.depiler();				 
				 	 // on modifie cet indice avec son branchement conditionnel
				 	 po.modifier(indice_si, po.getIpo()+1); 							
					 // empiler l'adresse de la valeur du saut dans la pile des reprises, elle sera completee
					 // lorsque le numero de ligne ou effectuer le branchement sera connue
					 pileRep.empiler(po.getIpo());					 														
					 break;
					
			// fin du SI
			case 38: // on depile ipo
					 int indice_fsi = pileRep.depiler();
					 // on modifie cet indice avec son branchement conditionnel
					 po.modifier(indice_fsi, po.getIpo()+1); 
					 break;
			
		    // execution des instructions de chaque COND
			case 39: // on recupere l'indice du BSIFAUX precedent
					 int indice = pileRep.depiler();
					 // on produire BINCOND
					 po.produire(BINCOND);
					 po.produire(0);
					 // on modifie l'indice de BSIFAUX
					 po.modifier(indice, po.getIpo()+1);					 					 
					 // on empile ensuite l'index de BINCOND
					 pileRep.empiler(po.getIpo());
					 suiteBincond.add(po.getIpo());
					 break;
					 
			// aucune instruction a la fin du COND
			case 40: po.modifier(pileRep.depiler(), po.getIpo()+1);
					 break;
					 
			// fin du COND
			case 41: // on parcourt notre liste pour mettre a jour les differents BINCOND
					 ListIterator<Integer> it = suiteBincond.listIterator(suiteBincond.size());
					 while(it.hasPrevious()) {
						int bincond = it.previous();
						if(bincond != 0) {
							po.modifier(bincond, po.getIpo()+1);
							pileRep.depiler();
							it.remove();
						}
						else {
							// on supprime le 0 de notre liste
							it.remove();
							// on depile le 0 enregistre
							pileRep.depiler();
							break;
						}
					 }
					 break;					 
			
			case 42: // Debut d'une instruction tant que, empiler l'adresse dans la pile d'instructions
		    		 // de l'expression conditionnelle dans la pile des reprises, pour s'y brancher a la fin de la boucle
					 pileRep.empiler(po.getIpo()+1);
					 break;
			
			// Sortie du ttq
			case 43: po.produire(BINCOND);
				 	 po.modifier(pileRep.depiler(), po.getIpo() + 2);
				 	 po.produire(pileRep.depiler());
					 break;
			
		    case 45: // Empiler 0 pour chaque instruction COND
					 pileRep.empiler(0);
					 // On enregistre 0 dans notre liste
					 suiteBincond.add(0);
					 break;
					 
		    case  50: // production du bincond avant d'entrer dans la procédure
	                  po.produire(BINCOND);
	                  po.produire(0);
	                  pileRep.empiler(po.getIpo());
	                  break;
	           
	        case 51: // depilement et mise a jour du bincond à la fin de la procédure
	                 po.modifier(pileRep.depiler(), po.getIpo()+1);
	                 break;
	                    
	        case 52: // declaration de la procedure
	        	 	 if(presentIdent(1) == 0){
	        	 		placeIdent(UtilLex.numId, PROC, NEUTRE, po.getIpo()+1);
	        	 		placeIdent(-1, PRIVEE, NEUTRE, 0);  // a mettre a jour a la fin de la declaration de la proce!dure
	        	 		bc = PtGen.it + 1;
	        	 		nbParam = 0;	                  
	        	 	 } else
	        	 		 UtilLex.messErr("Procedure " + UtilLex.repId(code) + " deja declaree.");
	        	 	break;
	                  
	        case 53: // declaration d'un parametre fixe
	                 if(presentIdent(bc)==0){
	                	paramInTabSymb ++;
	                	placeIdent(UtilLex.numId, PARAMFIXE, tCour, paramInTabSymb);
	                    nbParam++;
	                 }else
	                	 UtilLex.messErr("Paramètre fixe " + UtilLex.repId(UtilLex.numId) + " deja declaree."); 
	                break;
	               
	        case 54: // declaration d'un parametre modifiable 
	                if(presentIdent(bc) == 0){
	                	paramInTabSymb++;
	                    placeIdent(UtilLex.numId, PARAMMOD, tCour, paramInTabSymb);
	                    nbParam++;
	                    indexVar = 0;
	                }else
	                	UtilLex.messErr("Paramètre mod " + UtilLex.repId(UtilLex.numId) + " deja declaree.");  
	                break;
	               
	        case 55: // a la fin de la declaration de la procedure
	                /* Update le nombre de parametre et ensuite sauter 2 lignes pour les données de liaisons*/	        		
	        		if(tabSymb[bc-1].categorie == PRIVEE) {
	        			tabSymb[bc-1].info = nbParam;
	        		}
	        		// gestion de tabSymb.info des variables locales et des parametres
	        		paramInTabSymb +=2;
	                break;
	               
	        case 56: // fin de la procedure
	                // produire retour avec le nb de parametres,
	                // supprimer les variables locales
	                // anonymisation des parametres dans tabSymb
	                // mise a jour de bc a 1
	                po.produire(RETOUR);
	                po.produire(nbParam);
	                int tmp = bc;
	                while(tmp < PtGen.it) {
	                	tabSymb[tmp].code = -1;
	                	tmp ++;
	                }	                
	                // mise a jour de it
	                PtGen.it = PtGen.it - (indexVar + varConstanteInProc);
	                // mise a jour de bc
	                bc = 1;
	                indexVar = 0;
	                paramInTabSymb = -1;
	                break;
	                
	        // passage de parametre aux procedures
	        case 57: int paramId = presentIdent(1);
	        		 if(paramId == 0)  UtilLex.messErr("Parametre modifiable " + UtilLex.repId(code) + " non trouve.");
	        		 else
	        			 switch (tabSymb[paramId].categorie) {

							case PARAMMOD: po.produire(EMPILERADL);
										   po.produire(tabSymb[paramId].info);
										   po.produire(1);
										   break;

							case VARLOCALE: po.produire(EMPILERADL);
										    po.produire(tabSymb[paramId].info);
										    po.produire(0);
										    break;

							case VARGLOBALE: po.produire(EMPILERADG);
											 po.produire(tabSymb[paramId].info);
											 break;
							default:
		    					UtilLex.messErr("L'identifiant " + UtilLex.repId(UtilLex.numId) + " n'est pas passable comme parametre.");
		    					break;
						}
	        		  break;
	               
	        // point de generation pour l'appel d'une procedure 
	        case 58: if(varType == NEUTRE && varCat == PROC) {
	        		 	po.produire(APPEL);
	        		 	po.produire(varInfo);
	        		 	po.produire(tabSymb[identAffect + 1].info);	        		 	
	        		 }
	        		 break;
			// produit arret et creation du code Mapile 				
			case 200: po.produire(ARRET);
			  		  po.constGen();
			  		  po.constObj();
			  		  afftabSymb();
			  		  // remettre a jour tabSymb et les variables
			  		  for(int i = 0; i < tabSymb.length; i++) {
			  			  tabSymb[i] = null;
			  		  }
			  		  indexVar = 0;
			  		  identAffect = 0;
			  		  nbParam = 0; 
			  		  paramInTabSymb = -1; 
			  		  varConstanteInProc = 0;
			  		  break;

			default:
				System.out.println("Point de generation non prevu dans votre liste");
				break;

		}
	}
}