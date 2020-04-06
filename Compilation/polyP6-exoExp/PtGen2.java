/*********************************************************************************
 *   polyP6-exoExp : PtGen2 donne le code des traitements à effectuer            *
 *                   lors de l'analyse d'une expression                          *
 *   squelette de PtGen2 fourni aux étudiants, à compléter pour grammaire  Exp2  *
 *   nom de l'expression analysée, sans suffixe : String nomSource               *
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

    // initialisations  à  compléter 
    // -------------------------------------
 

    // code des points de génération à compléter
    // -------------------------------------
    public static void pt(int numGen) {
    	switch (numGen) {  
	    	case 0: break;
			
			// lecture de nbentier
			case 1: System.out.println(valNb); 
					break;
					
			// lecture de ident
            case 2: System.out.println(idLu); 
					break;
					
			// lecture de +
            case 3: System.out.println("+"); 
					break;
					
			// lecture de -
            case 4: System.out.println("-"); 
					break;
			// lecture de *
            case 5: System.out.println("*"); 
					break;
			// lecture de div
			case 6: System.out.println("div"); 
					break;

			// lecture de '('
			case 7: System.out.println("(");
					break;
					
			// lecture de ')'
			case 8: System.out.println(")");
					break;

    		default : System.out.println("Point de generation non prevu la liste");break;
    	
    	}
    }
}