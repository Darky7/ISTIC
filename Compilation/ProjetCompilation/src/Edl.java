import java.io.*;
import java.util.HashMap;


public class Edl {
	
	// nombre max de modules, taille max d'un code objet d'une unite
	static final int MAXMOD = 2, MAXOBJ = 1000;
	// nombres max de references externes (REF) et de points d'entree (DEF)
	// pour une unite
	private static final int MAXREF = 10, MAXDEF = 10;
	
	// typologie des erreurs
	private static final int FATALE = 0, NONFATALE = 1;
	
	// valeurs possibles du vecteur de translation
	private static final int TRANSDON=1,TRANSCODE=2,REFEXT=3;
	
	// table de tous les descripteurs concernes par l'edl
	static Descripteur[] tabDesc = new Descripteur[MAXMOD + 1];
	
	// declarations de variables A COMPLETER SI BESOIN
	static int ipo, nMod, nModule, nbErr;
	static String nomProg;
	
	static String[] FichierObj = new String[MAXMOD+1];
	static int[] transDon = new int[MAXMOD+1];
	static int[] transCode = new int[MAXMOD+1];
	static int[][] adFinale = new int[MAXMOD+1][MAXDEF+1];
	static Descripteur.EltDef[] dicoDef = new Descripteur.EltDef[(MAXMOD+1)*MAXDEF];
	static int flagDicoDef = 0;
			
	private static int definDico(String nomProc){
		for (int i=1;i<=flagDicoDef;i++){
			if(nomProc.equals(dicoDef[i].nomProc))
				return i;
		}
		return 0;
	}
	
	// utilitaire de traitement des erreurs
	// ------------------------------------
	static void erreur(int te, String m) {
		System.out.println(m);
		if (te == FATALE) {
			System.out.println("ABANDON DE L'EDITION DE LIENS");
			System.exit(1);
		}
		nbErr = nbErr + 1;
	}

	// utilitaire de remplissage de la table des descripteurs tabDesc
	// --------------------------------------------------------------
	static void lireDescripteurs() {
		String s;
		System.out.println("les noms doivent etre fournis sans suffixe");
		System.out.print("nom du programme : ");
		s = Lecture.lireString();
		tabDesc[0] = new Descripteur();
		tabDesc[0].lireDesc(s);
		if (!tabDesc[0].getUnite().equals("programme"))
			erreur(FATALE, "programme attendu");
		nomProg = s;
		nModule = 0;
		nMod = 0;
		while (!s.equals("") && nMod < MAXMOD) {
			System.out.print("nom de module " + (nMod + 1)
					+ " (RC si termine) ");
			s = Lecture.lireString();
			if (!s.equals("")) {
				nMod = nMod + 1;
				tabDesc[nMod] = new Descripteur();
				tabDesc[nMod].lireDesc(s);			
				
				// fichier module				
				FichierObj[nModule] = s;
				nModule = nModule + 1;
				
				if (!tabDesc[nMod].getUnite().equals("module"))
					erreur(FATALE, "module attendu");
				
				// Mettre a jour les tableaux Transdon et Transcode
				transDon[nMod] = transDon[nMod -1] + tabDesc[nMod -1].getTailleGlobaux();
				transCode[nMod] = transCode[nMod -1]+ tabDesc[nMod -1].getTailleCode();
				
				// Maintenant on va ajouter tabDef dans le dicoDef
				for(int i=1; i<= tabDesc[nMod].getNbDef();i++){
					
					//Si pas de double déclaration  alors
					if(definDico(tabDesc[nMod].tabDef[i].nomProc)==0){
						//On va récupérer chaque ligne du tabDef et l'ajouter à l'adresse de la procédure de l'ancien ipo
						dicoDef[flagDicoDef+i] = tabDesc[nMod].tabDef[i];
						dicoDef[flagDicoDef+i].adPo +=transCode[nMod];
							
					}
					else						
						erreur(NONFATALE, "Double déclaration de "+tabDesc[nMod].tabDef[i].nomProc);
				}
				// incrementer le flag de dicoDef
				flagDicoDef+=tabDesc[nMod].getNbDef();				
			}
		}
	}
	
	static void constMap() {	
		// f2 = fichier executable .map construit
		OutputStream f2 = Ecriture.ouvrir(nomProg + ".map");
		if (f2 == null)
			erreur(FATALE, "creation du fichier " + nomProg
					+ ".map impossible");
		// pour construire le code concatene de toutes les unites
		int[] po = new int[(nMod + 1) * MAXOBJ + 1];
		
		ipo=1;			
		
		for( int i = 0; i < nModule; i++) {
			//ouverture des moules
			InputStream fichierCourant = Lecture.ouvrir(FichierObj[i] + ".obj");
			if (fichierCourant == null) {
				erreur(FATALE, FichierObj[i] + ".obj impossible a ouvrir");
			}

			// Creer un nouveau tableau associatif de transitions
			HashMap<Integer, Integer> tabTrans = new HashMap<Integer, Integer>();

			// Variables pour la lecture des transitions
			int adresse;
			int type_trans;
			int[] vTrans = new int[MAXOBJ + 1];
						
			//on recupere les translations  qui sont en debut de fichier
			for (int j = 0; j<tabDesc[i].getNbTransExt(); j++) {
				
				// Recupere chaque couples
				adresse = Lecture.lireInt(fichierCourant) + transCode[i];
				type_trans = Lecture.lireIntln(fichierCourant);

				// Ajoute le couple
				tabTrans.put(adresse, type_trans);
				int it = Lecture.lireInt(fichierCourant) + transCode[i];
				vTrans[it] = Lecture.lireIntln(fichierCourant);
			}
			// Valeurs temporaire
			int ref_ext = 1, derniere_instruction = tabDesc[i].getTailleCode();
			Integer res_get;
			// Ne prends pas la toute derniere instruction (vide)
			if (i == nMod) derniere_instruction = tabDesc[i].getTailleCode()-1;
			for (int j = 0; j < derniere_instruction; j++) {
				
				po[ipo] = Lecture.lireIntln(fichierCourant);				
				
				System.out.println(FichierObj[i]);
				
				System.out.println(po[ipo]);
				
				// Si contenu dans le vecteur de translation
				res_get = tabTrans.get(ipo);
				if (res_get != null) {

					// En fonction du type de transition
					switch (res_get.intValue()) {

						case TRANSDON:
							po[ipo] += transDon[i];
							break;

						case TRANSCODE:
							po[ipo] += transCode[i];
							break;

						case REFEXT:
							po[ipo] = adFinale[i][ref_ext];
							ref_ext++;
							break;
					}
					
				}
				ipo++;
			}					

			System.out.println("done");
			
			Lecture.fermer(fichierCourant);			
		}
		//	on enlève la dernière incrementation car plus de module à ajouter
		ipo--;

		//mise à jour du nombre total de variables globales pour le RESERVER
		po[2] = transDon[nMod] + tabDesc[nMod].getTailleGlobaux();

		//on vient écrire les modifications sur le fichier
		for (int i = 1; i<ipo; i++) {
			Ecriture.ecrireStringln(f2, ""+po[i]);
		}

		Ecriture.fermer(f2);
		// creation du fichier en mnemonique correspondant
		Mnemo.creerFichier(ipo, po, nomProg + ".ima");
	}
	
	
	/**
	 * Affiche les tables
	 */
	static void printTables() {

		System.out.println("\n Table TransDon:");
		for (int i = 0; i <= nMod; i++)
			System.out.println("[" + i + "]" + " => " + transDon[i]);

		System.out.println("\n Table TransCode:");
		for (int i = 0; i <= nMod; i++)
			System.out.println("[" + i + "]" + " => " + transCode[i]);

		System.out.println("\n Table DicoDef:");
		for (int i = 1; i <= flagDicoDef; i++)
			System.out.println("[" + i + "]" + " => (" + dicoDef[i].nomProc + ", " + dicoDef[i].adPo + ", " + dicoDef[i].nbParam + ")");

		System.out.println("\n Table AdFinale:");
		for (int y = 0; y <= nMod; y++) {
			System.out.print("[" + y + "]" + " => ");

			for (int x = 1; x <= tabDesc[y].getNbRef(); x++)
				System.out.print("[" + adFinale[y][x] + "] ");

			if (tabDesc[y].getNbRef() == 0)
				System.out.print("Pas de reference");

			System.out.println("");
		}
	}


	public static void main(String argv[]) {
		System.out.println("EDITEUR DE LIENS / PROJET LICENCE");
		System.out.println("---------------------------------");
		System.out.println("");
		nbErr = 0;
		
		// Phase 1 de l'edition de liens
		// -----------------------------
		lireDescripteurs();		// lecture des descripteurs a completer si besoin
		
		transDon[0] = 0;
		transCode[0] = 0;
		// Associe les refs aux defs
				for (int i = 0; i <= nMod; i++) {

					// Parcours tabRef
					for (int j = 1; j <= tabDesc[i].getNbRef(); j++) {

						// Cherche ref
						int ref_id = definDico(tabDesc[i].tabRef[j].nomProc);

						// Si non present dans les defs, erreur
						if (ref_id == 0)
							erreur(NONFATALE, "Reference sur procedure non definie");

						// Si ok, l'ajoute a adFinale
						else
							adFinale[i][j] = dicoDef[ref_id].adPo;
					}
				}

		if (nbErr > 0) {
			System.out.println("programme executable non produit");
			System.exit(1);
		}
		// Affiche toutes les tables
		printTables();
		// Phase 2 de l'edition de liens
		// -----------------------------
		constMap();
		System.out.println("Edition de liens terminee");
	}
}