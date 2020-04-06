/*********************************************************************************
 *   polyP6-exoExp : PtGen2 donne le code des traitements � effectuer            *
 *                   lors de l'analyse d'une expression                          *
 *   squelette de PtGen2 fourni aux �tudiants, � compl�ter pour grammaire  Exp2  *
 *   nom de l'expression analys�e, sans suffixe : String nomSource               *
 *   ----------------------------                                                *
 *                                                                               *
 *   attributs lexicaux (selon items figurant dans la grammaire):                *
 *   ------------------                                                          *
 *     int PtGen2.valNb = valeur du dernier nombre entier lu (item nbentier)     *
 *     int PtGen2.numId = code du dernier identificateur lu (item ident)         *
 *                                                                               *
 *                                      A. GRAZON - V.MASSON                     *
 *********************************************************************************/


import java.io.*;

public class PtGen2 {
    

    public static String trinome = "a completer";
    public static int valNb;
    public static String idLu;

    // initialisations  �  compl�ter 
    // -------------------------------------
 

    // code des points de g�n�ration � compl�ter
    // -------------------------------------
    public static void pt(int numGen) {
    	switch (numGen) {  
	    	case 0: break;
			
			// lecture de nbentier
			case 1: System.out.println("empiler "+ valNb); 
					break;
					
			// lecture de ident
            case 2: System.out.println("contenug "+idLu); 
					break;
					
			// lecture de +
            case 3: System.out.println("add"); 
					break;
					
			// lecture de -
            case 4: System.out.println("sous"); 
					break;
			// lecture de *
            case 5: System.out.println("mul"); 
					break;
			// lecture de div
			case 6: System.out.println("div"); 
					break;

    		default : System.out.println("Point de generation non prevu la liste");break;
    	
    	}
    }
}