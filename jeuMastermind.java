package mastermind;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class jeuMastermind {

	//Variables permettant de modifier rapidement les conditions de jeu
	//Le panel des couleurs, nombre de couleurs dans la combinaison, nombre d'essais
	static Scanner sc = new Scanner(System.in);
	static String [] TAB_REF_COLORS = {
			"rouge", "jaune", "vert", "bleu", "orange", "blanc", "violet", "fuchsia"};
	static int NB_COLORS = 4, NB_TEST=12;
	
	public static void main(String[] args) {
		boolean replay=true;
		String [] secretCombination = new String[NB_COLORS];
		ArrayList<String>tabCombinations = new ArrayList<String>();
		
		System.out.println("**************************");
		System.out.println("***     MASTERMIND     ***");
		System.out.println("**************************\n");
		
		readRulesOfGame();
		
		while(replay) {
			generateRandomCombination(secretCombination);
			playGame(secretCombination, tabCombinations);
			if(replayGame(replay)==false) {
				break;
			}
			//réinitialisation de l'arrayList "tabCombinations"
			tabCombinations.clear();
		}
		sc.close();	
	}
	
	// Lire les règles du jeu
	static void readRulesOfGame() {
		boolean rulesOfGame=false;
		String answer;
		
		do	{
			System.out.println("Voulez-vous lire les règles du jeu ' Mastermind ' ?\nTapez 'oui' ou 'non' : ");
			answer = sc.nextLine();
		
		// Gestion de la réponse
			if (answer.equalsIgnoreCase("oui")) {
				System.out.println("Vous avez 12 tentatives pour trouver la combinaison de 4 couleurs.\n"
						+ "Si votre proposition n'est pas la bonne,\n"
						+ "vous aurez le nombre de bonnes couleurs mal placées\n"
						+ "ainsi que le nombre de bonnes couleurs bien placées.\n"
						+ "À savoir qu'une couleur ne peut être utilisée qu'une seule fois dans la combinaison.\n"
						+ "et que le nombre de bonnes couleurs ne comprend pas celles qui sont bien placées.\n"
						+ "Bonne chance et soyez méthodique !!!\n");
				break;
			}else if (answer.equalsIgnoreCase("non")) {
				System.out.println("Bonne chance et soyez méthodique !!!\n");
				break;
			}else {	
				System.out.println("Erreur, vous n'avez pas saisi 'oui' ou 'non'.\n");
			}
		}while (!rulesOfGame);
	}
	
	// Création aléatoire d'une combinaison de 4 couleurs 
	static String [] generateRandomCombination(String Combination[]) {
		int currentPosition = 0;
		while(currentPosition!=NB_COLORS) {
			int indexRandom = (int)(Math.random()*TAB_REF_COLORS.length);
			String color = TAB_REF_COLORS[indexRandom];
			if(!isIn(color, Combination)) {
				Combination[currentPosition] = color;
				currentPosition++;
			}
		}
		return Combination;
	}

	static boolean isIn(String iStringToFind, String [] iTab) {
		int size = iTab.length;
		for(int i=0;i<size;i++) {
			if(iStringToFind.equals(iTab[i]))return true;
		}
		return false;
	}
	
	// Programme d'exécution du jeu 'Mastermind'
	static void playGame(String [] iSecretCombination, ArrayList<String> iTabCombinations) {
		String [] combinationTest = new String [NB_COLORS];
		String temp;
		
		// Récupération des combinaisons
		for (int i=1;i<=NB_TEST;i++) {
			System.out.println("\nEssai N° : "+(i));
			System.out.println("Choisis 4 couleurs parmi celles proposées en dessous:");
			for(int k=0; k<TAB_REF_COLORS.length; k++) {
				System.out.print(TAB_REF_COLORS[k]+" | ");
			}
			System.out.println();
			iTabCombinations.add("Essai N° "+(i));
			for (int j=0;j<NB_COLORS;j++) {	
				System.out.println("Veuillez taper le nom entier de votre couleur.");
				System.out.print("Entrez votre couleur n° "+(j+1)+" : ");
				temp=sc.nextLine();
				while(checkColors(temp, TAB_REF_COLORS)==false) {
					if(checkColors(temp, TAB_REF_COLORS)==true) {
						break;
					}else {
						System.out.println("Erreur, la couleur que vous avez saisie n'est pas référencée dans les couleurs énumérées");
						for(int l=0; l<TAB_REF_COLORS.length; l++) {
							System.out.print(TAB_REF_COLORS[l]+" | ");
						}
						System.out.println("\nVeuillez taper le nom entier de votre couleur : ");
						temp=sc.nextLine();
					}
				}
				combinationTest[j]=temp;
				adaptiveSpacing(combinationTest[j], iTabCombinations);
			}
			
			if (verifCombination(combinationTest, iSecretCombination, iTabCombinations)==true) {
				displayTable(iTabCombinations);
				System.out.println("Bravo, vous avez trouvé la bonne combinaison en seulement "+i+" essai(s) !!!");
				for (int k=0; k<NB_COLORS; k++) {
					System.out.print(iSecretCombination[k]+" | ");
				}
				break;
			}
			else if(i==NB_TEST) {
				displayTable(iTabCombinations);
				System.out.println("Vous avez perdu! :(");
				System.out.println("Vous n'avez pas réussi à trouver la bonne combinaison en moins de 12 essais.");
				System.out.print("La combinaison que vous auriez dû trouver est : ");
				for (int k=0; k<NB_COLORS;k++) {
					System.out.print(iSecretCombination[k]+" | ");	
				}
				break;
			}
			displayTable(iTabCombinations);
		}
	}
	
	// Vérification de la couleur transmise par l'utilisateur.
	static boolean checkColors(String temp, String [] TAB_REF_COLORS) {	
		for (int i=0; i<TAB_REF_COLORS.length; i++) {
			if(temp.equalsIgnoreCase(TAB_REF_COLORS[i])) {
				return true;
			}
		}
		return false;
	}
	
	// Mise en place des colonnes de tabCombinations
	static void adaptiveSpacing(String userColor, ArrayList<String>iTabCombinations) {
		int size=9, numberSpacing=0;
		for (int i=0; i<userColor.length(); i++) {
			numberSpacing=size-(userColor.length());
		}
		iTabCombinations.add((userColor+addSpacing(numberSpacing)));
	}
	
	// Ajout du nombre d'espace à la colonne
	static String addSpacing(int iNumberSpacing) {
		String adaptiveSpacing = "";
		for (int iDash=0;iDash<iNumberSpacing;iDash++) {
			adaptiveSpacing+=" ";
		}
		return adaptiveSpacing;
	}
	
	// Vérification de la combinaison
	static boolean verifCombination(String []tab1, String []tab2, ArrayList<String>tab3) {
		int countGoodPlaces=0, countGoodColors=0;
		for (int i=0; i<NB_COLORS; i++) {
			if (tab1[i].equalsIgnoreCase(tab2[i])) {
				countGoodPlaces++;	
			}
			//Vérification bonne(s) couleur(s) 
			for (int j=0; j<NB_COLORS; j++) {
				if (tab1[i].equalsIgnoreCase(tab2[j]) && i!=j) {
					countGoodColors++;
				}
			}
		}
		String goodPlaces=Integer.toString(countGoodPlaces);
		tab3.add("   "+goodPlaces+"   ");
		String goodColors=Integer.toString(countGoodColors);
		tab3.add("   "+goodColors+"   ");
		if(countGoodPlaces==4) {
			return true;
		}else {
			return false;
		}
	}	
	
	// Affichage du Tableau des propositions 
	static void displayTable(ArrayList<String> tabCombinations) {
		System.out.println("\n\t\t*********** TABLEAU DE VOS PROPOSITIONS ***********\n");	
						
		int size=NB_COLORS+3;
		for (int start=0; start<tabCombinations.size(); start +=size) {
			int end = Math.min(start + size, tabCombinations.size());
			List<String> sublist = tabCombinations.subList(start, end);
			String formattedString = sublist.toString()
					.replace(",", " | ")
					.replace("[", "")
					.replace("]", " |");
			System.out.println(formattedString);
		}
		System.out.print("Couleurs   |"+"    N° 1    "+"|"+"    N° 2    "+"|"+"    N° 3    "+"|"+"    N° 4    "+"|"+"placée(s)"+" |"+"couleur(s)"+"|\n");
		System.out.println();
	}
	
	
	// Rejouer ou non une autre partie
	static boolean replayGame(boolean newGame) {
		String answer;
		do {
			System.out.println();
			System.out.println("\nVoulez-vous rejouer une partie de ' Mastermind ' ?");
			System.out.println("Tapez 'oui' ou 'non' : ");
			answer = sc.nextLine();
			
			// Gestion de la réponse
			if (answer.equalsIgnoreCase("oui")) {
				System.out.println("Bonne chance et soyez méthodique !!!\n");
				System.out.println("NOUVELLE PARTIE !!!");
				return newGame=true;
			}else if (answer.equalsIgnoreCase("non")) {
				System.out.println("Merci d'avoir jouer à notre jeu 'Mastermind'");
				return newGame=false;
			}else {
				System.out.print("Erreur, vous n'avez pas saisi 'oui' ou 'non'.");
				newGame=false;
			}
		}while (!newGame);
		return newGame=false;
	}
	
}