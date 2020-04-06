/*
 * TP3 : Exercice 1
 * AGUESSY Onésime
 * DONOU Dario 
 */
public class Fourmis {
	/* Fonction next */
	public static String next(String uo){
        int cpt=1;
        String suite = "";
        for (int i=1;i<uo.length();i++){
			if (uo.charAt(i) == uo.charAt(i-1)){
				cpt++;
			}else {
				suite = suite + cpt + uo.charAt(i-1);
				cpt=1;
			}
	    }	    	
        suite = suite + cpt + uo.charAt(uo.length()-1); 
        return suite;
    }
 
	/* Fonction main qui affiche la suite */
	public static void main(String[] args) {
		int i = 1;
		String uo = "1";
		System.out.println("u" + "0" + " = "+ uo);
		
		while(i != 10) {
			uo = next(uo);
			System.out.println("u" + i + " = " +uo);
			i++;
		}
		
	}

}
