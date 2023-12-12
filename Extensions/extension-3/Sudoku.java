import java.util.*;
import java.lang.*;
import java.io.IOException; 
import java.io.BufferedReader; 
import java.io.FileReader; 

public class Sudoku {

    private static Scanner scanner = new Scanner(System.in);
    
    public static int[][] tabTrous = new int[82][2];
    public static int valremplie = 0;

    //.........................................................................
    // Fonctions utiles
    //.........................................................................

    public static boolean push(int[] trou) {
        tabTrous[0][0] = ++valremplie;
        boolean mis = false;
        int ligne = 1;
        while (ligne < 82 && !mis && trou.length == 2) {
            if (tabTrous[ligne][0] == 0 && tabTrous[ligne][1] == 0) {
                tabTrous[ligne][0] = trou[0];
                tabTrous[ligne][1] = trou[1];
                mis = true;
            }
            ligne++;
        }
        return mis;
    }

    public static int[] pull() {
        if (valremplie == 0) return null;
        else {
            int [] trou = {tabTrous[valremplie][0],tabTrous[valremplie][1]};
            tabTrous[valremplie][0] = 0;
            tabTrous[valremplie][1] = 0;
            valremplie--;
            return trou;
        }
    }

    /*public static boolean estVide() {
        int ligne = 0;
        boolean autreQueZero = false;
        while (ligne <= 82 && !autreQueZero) {
            if (tabTrous[ligne][0] != 0 || tabTrous[ligne][1] != 0) {autreQueZero = true;}
        }
        return !autreQueZero;
    }*/

    /** pré-requis : min <= max
     *  résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
     */
    public static int saisirEntierMinMax(int min, int max){
	//________________________________________________________
        int k = scanner.nextInt();
        while (k < min || k > max) {
            System.out.print("Saisir une valeur correcte :");
            k = scanner.nextInt();
        }
        return k;
    }  // fin saisirEntierMinMax
    //.........................................................................

    /** MODIFICI
     *  pré-requis : mat1 et mat2 ont les mêmes dimensions
     *  action : copie toutes les valeurs de mat1 dans mat2 de sorte que mat1 et mat2 soient identiques
     */
    public static void copieMatrice(int[][] mat1, int[][] mat2){
        for (int i = 0; i < mat1.length; i++) {
            for (int j = 0; j < mat1[i].length; j++) {
                if (mat2[i][j] != mat1[i][j]) {mat2[i][j] = mat1[i][j];}
            }
        }
    }  // fin copieMatrice

    //.........................................................................

    /** pré-requis :  k >= 0
     *  résultat : un tableau de booléens représentant le sous-ensemble de l'ensemble des entiers 
     *             de 1 à k égal à lui-même 
     */
    public static boolean[] ensPlein(int k){
	//_____________________________________
        boolean[] TBool = new boolean[k+1];
        for (int i = 0; i <= k; i++){
            TBool[i] = true;
        }
        return TBool;
    }  // fin ensPlein

    /** pré-requis : 1 <= val < ens.length
     *  action :     supprime la valeur val de l'ensemble représenté par ens, s'il y est
     *  résultat :   vrai ssi val était dans cet ensemble
     */
    public static boolean supprime(boolean[] ens, int val){
        if (ens[val]) {
            ens[val] = false;
            return true;
        }
        else return false;
    }  // fin supprime


    //.........................................................................


    /** pré-requis : l'ensemble représenté par ens k'est pas vide
     *  résultat :   un élément de cet ensemble
     */
    public static int uneValeur(boolean[] ens){
	//_____________________________________________
        boolean trouve = false;
        int i = 1;
        while (i < ens.length && trouve == false) {
            if (ens[i]) trouve = true;
            else i++;
        }
        return i;
    }  // fin uneValeur

    //.........................................................................

    /**
 
       1 2 3 4 5 6 7 8 9
       ------------------- 
       1 |6 2 9|7 8 1|3 4 5|
       2 |4 7 3|9 6 5|8 1 2|
       3 |8 1 5|2 4 3|6 9 7|
       ------------------- 
       4 |9 5 8|3 1 2|4 7 6|
       5 |7 3 2|4 5 6|1 8 9|
       6 |1 6 4|8 7 9|2 5 3|
       ------------------- 
       7 |3 8 1|5 2 7|9 6 4|
       8 |5 9 6|1 3 4|7 2 8|
       9 |2 4 7|6 9 8|5 3 1|
       ------------------- 

 
       1 2 3 4 5 6 7 8 9
       ------------------- 
       1 |6 0 0|0 0 1|0 4 0|
       2 |0 0 0|9 6 5|0 1 2|
       3 |8 1 0|0 4 0|0 0 0|
       ------------------- 
       4 |0 5 0|3 0 2|0 7 0|
       5 |7 0 0|0 0 0|1 8 9|
       6||0 0 0|0 7 0|0 0 3|
       ------------------- 
       7 |3 0 0|0 2 0|9 0 4|
       8 |0 9 0|0 0 0|7 2 0|
       9 |2 4 0|6 9 0|0 0 0|
       ------------------- 


       * pré-requis : 0<=k<=3 et g est une grille k^2xk^2 dont les valeurs sont comprises 
       *              entre 0 et k^2 et qui est partitionnée en k sous-carrés kxk
       * action : affiche la  grille g avec ses sous-carrés et avec les numéros des lignes 
       *          et des colonnes de 1 à k^2.
       * Par exemple, pour k = 3, on obtient le dessin d'une grille de Sudoku
       *  
       */
    public static void afficheGrille(int k,int[][] g){
	//__________________________________________________
        if (k != 0) {
            System.out.print("   ");
            for (int i=1; i <= k*k; i++) {
                if (i != k*k) System.out.print(i + " ");
                else System.out.print(i);
            }
            System.out.println();
            for (int i = 0; i<= k*k*2+2; i++) {System.out.print("-");}
            System.out.println();
            for (int i = 0; i < g.length; i++) {
                System.out.print((i+1) + " |");
                for (int j = 0; j < g[i].length; j++) {
                    if ((j+1)%k == 0) System.out.print(g[i][j] + "|");
                    else System.out.print(g[i][j] +" ");
                }
                System.out.println();
                if ((i+1)%k == 0) {
                    for (int j = 0; j<= k*k*2+2; j++) {System.out.print("-");}
                    System.out.println();
                }
            }
        }
    } // fin afficheGrille
    //.........................................................................

    /** pré-requis : k > 0, 0 <= i< k^2 et 0 <= j < k^2
     *  résultat : (i,j) étant les coordonnées d'une case d'une grille k^2xk^2 partitionnée 
     *             en k sous-carrés kxk, retourne les coordonnées de la case du haut à gauche
     *             du sous-carré de la grille contenant cette case.
     *  Par exemple, si k=3, i=5 et j=7, la fonction retourne (3,6).
     */
    public static int[] debCarre(int k,int i,int j){
	//__________________________________________________
        int[] Indice = new int[2];
        int iCarre = (i/k); //la ligne du sous-carré -> quotient rond
        int jCarre = (j/k); //la colonne du cous-carré -> quotient rond
        Indice[0] = (iCarre*k); //quotient rond*k donne la position ligne 0 dans sous-carré
        Indice[1] = (jCarre*k); //quotient rond*k donne la position colonne 0 dans sous-carré
        return Indice;
    }  // fin debCarre


    //.........................................................................

    // Initialisation
    //.........................................................................

    /** MODIFICI
     *  pré-requis : gComplete est une matrice 9X9
     *  action   :   remplit gComplete pour que la grille de Sudoku correspondante soit complète
     *  stratégie :  les valeurs sont données directement dans le code et on peut utiliser copieMatrice pour mettre à jour gComplete
     */
    public static void initGrilleComplete(int [][] gComplete){
        int [][] grille = 
          {{6,2,9,7,8,1,3,4,5},
		   {4,7,3,9,6,5,8,1,2},
		   {8,1,5,2,4,3,6,9,7},
		   {9,5,8,3,1,2,4,7,6},
		   {7,3,2,4,5,6,1,8,9},
		   {1,6,4,8,7,9,2,5,3},
		   {3,8,1,5,2,7,9,6,4},
		   {5,9,6,1,3,4,7,2,8},
		   {2,4,7,6,9,8,5,3,1}};
        copieMatrice(grille, gComplete);
    } // fin initGrilleComplete

    //.........................................................................

    /** MODIFICI
     *  pré-requis : gSecret est une grille de Sudoku complète de mêmes dimensions que gIncomplete et 0 <= nbTrous <= 81
     *  action :     modifie gIncomplete pour qu'elle corresponde à une version incomplète de la grille de Sudoku gSecret (gIncomplete peut être complétée en gSecret), 
     *               avec nbTrous trous à des positions aléatoires
     */
    public static void initGrilleIncomplete(int nbTrous, int [][] gSecret, int[][] gIncomplete){
	//___________________________________________________________________________
        copieMatrice(gSecret, gIncomplete);
        for (int i = 1; i <= nbTrous; i++) {
            int ligne = Ut.randomMinMax(0, gIncomplete.length-1);
            int colonne = Ut.randomMinMax(0, gIncomplete[ligne].length-1);
            if (gIncomplete[ligne][colonne] == 0) {
                i--;
            }
            else gIncomplete[ligne][colonne] = 0;
        }
    } // fin initGrilleIncomplete
	
    //.........................................................................

    /** MODIFICI
     *  pré-requis : 0 <= nbTrous <= 81 ; g est une grille 9x9 (vide a priori)
     *  action :   remplit g avec des valeurs saisies au clavier comprises entre 0 et 9
     *               avec exactement nbTrous valeurs nulles
     *               et avec re-saisie jusqu'à ce que ces conditions soient vérifiées.
     *               On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
     *  stratégie : utilise la fonction saisirEntierMinMax
     */
    public static void saisirGrilleIncomplete(int nbTrous, int [][] g){
	//_________________________________________________
        int trou = 0;
        int k = 0;
        int remplis = 0;
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                int restant = g.length*g.length - remplis;
                if(restant == nbTrous - trou) {
                    g[i][j] = 0;
                    remplis++;
                    trou++;
                }
                else {
                    if (trou < nbTrous) {
                        k = saisirEntierMinMax(0,9);
                        g[i][j] = k;
                        if (k == 0) trou++;
                    }
                    else if (trou == nbTrous){
                            k = saisirEntierMinMax(1,9);
                            g[i][j] = k;
                    }
                    remplis++;
                }
            }
        }
    } // fin saisirGrilleIncomplete

    //.........................................................................

    /** MODIFICI
     *  pré-requis : 0 <= nbTrous <= 81 ; g est une grille 9x9 (vide a priori) ; 
     *               fic est un nom de fichier de ce répertoire contenant des valeurs de Sudoku
     *  action :   remplit g avec les valeurs lues dans fic. Si la grille ne contient pas des valeurs 
     *             entre 0 et 9 ou n'a pas exactement nbTrous valeurs nulles, la méthode doit signaler l'erreur,
     *             et l'utilisateur doit corriger le fichier jusqu'à ce que ces conditions soient vérifiées.
     *             On suppose dans la version de base que la grille saisie est bien une grille de Sudoku incomplète.
     */
    public static void saisirGrilleIncompleteFichier(int nbTrous, int [][] g, String fic){
	//_________________________________________________

	try (BufferedReader lecteur = new BufferedReader(new FileReader(fic))) {  
        int trou = 0;
	    for (int i = 0 ; i < 9 ; i++){
		String ligne = lecteur.readLine();
		String [] valeurs = ligne.split("\\s+");
		for (int j = 0 ; j < 9 ; j++) {
            int valeur = Integer.parseInt(valeurs[j]);
                if (valeur < 0 || valeur > 9) {
                    System.out.println("Erreur : La valeur doit être entre 0 et 9 inclus");
                    return;
                }
		    g[i][j] = valeur;
            if (valeur == 0) {trou++;}
		}
	    }
        if (trou > nbTrous) {System.out.println("Attention il y a trop de trous ! Veuillez corriger");}
        if (trou < nbTrous) {System.out.println("Attention il n'y a pas assez de trous ! Veuillez corriger");}
	} catch (IOException e) {
	    e.printStackTrace();
	}
    } // fin saisirGrilleIncompleteFichier


    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : met dans valPossibles l'ensemble des entiers de 1 à 9 pour chaque trou de gOrdi
     *           et leur nombre dans nbValPoss       
     */
    public static void initPleines(int [][] gOrdi, boolean[][][] valPossibles, int [][] nbValPoss){
	//________________________________________________________________________________________________
        for(int x=0; x<gOrdi.length; x++){ //parcours des lignes
            for(int y=0; y<gOrdi.length; y++){ //parcours des colonnes 
                if(gOrdi[x][y] == 0){ //Si il rencontre un trou
                    nbValPoss[x][y] = 9; //change le nb de valeurs possibles dans nbValPoss à 9
                    valPossibles[x][y] = ensPlein(9); //remplace chaque valeur du tab de boolean correspondant au trou par true
                }
            }
        }
    }  // fin initPleines

    //.........................................................................


    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               0 <= i < 9, 0 <= j < 9, gOrdi[i][j] > 0,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : supprime dans les matrices valPossibles et nbValPoss la valeur gOrdi[i][j] pour chaque case de la ligne,
     *           de la colonne et du carré contenant la case (i,j) correspondant à un trou de gOrdi.
     */
    public static void suppValPoss(int [][] gOrdi, int i, int j, boolean[][][] valPossibles, int [][]nbValPoss){
	//_____________________________________________________________________________________________________________
        int nb = gOrdi[i][j];
        //modif sur la ligne
        for (int a = 0; a < gOrdi.length; a++) {
            if (gOrdi[i][a] == 0 && supprime(valPossibles[i][a], nb)) {
                nbValPoss[i][a] -= 1;
            }
        }
        //modif sur la colonne
        for (int b = 0; b < gOrdi.length; b++) {
            if (gOrdi[b][j] == 0 && supprime(valPossibles[b][j], nb)) {
                nbValPoss[b][j] -= 1;
            }
        }
        //modif carré
        int [] tablo = debCarre(3, i, j);
        for (int a = tablo[0]; a <= (tablo[0]+2); a++) {
            for (int b = tablo[1]; b <= (tablo[1]+2); b++) {
                if (gOrdi[a][b] == 0 && supprime(valPossibles[a][b], nb)) {
                    nbValPoss[a][b] -= 1;
                }
            }
        }
    }// fin suppValPoss


    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     * action :      met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss       
     */
    public static void initPossibles(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){
	//________________________________________________________________________________________________
        int n = gOrdi.length; //Stocke la longueur

        initPleines(gOrdi, valPossibles, nbValPoss); //Initialise la grille

        for(int i = 0; i < n; i++){ //Supprime les valeurs pas possibles
            for(int j = 0; j < n; j++){
                if (gOrdi[i][j] != 0) suppValPoss(gOrdi, i, j, valPossibles, nbValPoss);

            }
        }
    }  // fin initPossibles

    //.........................................................................


    /** pré-requis : gSecret, gHumain et gOrdi sont des grilles 9x9
     *  action :     - demande au joueur humain de saisir le nombre nbTrous compris entre 0 et 81,
     *               - met dans gSecret une grille de Sudoku complète,
     *               - met dans gHumain une grille de Sudoku incomplète, pouvant être complétée en gSecret
     *                  et ayant exactement nbTrous trous de positions aléatoires,
     *               - met dans gOrdi une grille de Sudoku incomplète saisie par le joueur humain
     *                  ayant  nbTrous trous,
     *               - met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *                  et leur nombre dans nbValPoss.
     * retour : la valeur de nbTrous
     */
    public static int initPartie(int [][] gSecret, int [][] gHumain, int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){
	//______________________________________________________________________________________________
        System.out.println("Saisir le nombre de trous (entre 0 et 81): ");
        int nbTrous = saisirEntierMinMax(0, 81);
        initGrilleComplete(gSecret);
        initGrilleIncomplete(nbTrous, gSecret, gHumain);
        saisirGrilleIncompleteFichier(nbTrous, gOrdi, "grille1.txt");
        /*saisirGrilleIncomplete(nbTrous, gOrdi);*/
        initPossibles(gOrdi, valPossibles, nbValPoss);
        chercheTrou(gOrdi, nbValPoss);
        return nbTrous;
    }
	
    //...........................................................
    // Tour du joueur humain
    //...........................................................

    /** pré-requis : gHumain est une grille de Sudoju incomplète pouvant se compléter en 
     *               la  grille de Sudoku complète gSecret
     *
     *  résultat :   le nombre de points de pénalité pris par le joueur humain pendant le tour de jeu
     *
     *  action :     effectue un tour du joueur humain   
     */
    public static int tourHumain(int [][] gSecret, int [][] gHumain){
	//___________________________________________________________________
        int penalite = 0;
        boolean Check = false;
        while (!Check) {
            System.out.print("Entrez une ligne : ");
            int L = saisirEntierMinMax(1, 9);
            L--;; //Correction d'indice
            System.out.print("Entrez une colonne : ");
            int C = saisirEntierMinMax(1, 9);
            C--; //Correction d'indice
            if (gHumain[L][C] == 0) {
                System.out.println("Voulez vous remplir ou joker? ");
                System.out.println("Joker : 0, Remplir : autre nombre avec lequel vous voulez remplir");
                System.out.print("Votre réponse : ");
                int Rep = saisirEntierMinMax(0, 9);
                if(Rep != 0){
                    if(Rep != gSecret[L][C]){
                        penalite++;
                        System.out.println("Pénalité ! Ce n'est pas la bonne réponse");
                    }else{
                        gHumain[L][C] = Rep;
                        Check = true;
                    }
                }else{
                    gHumain[L][C] = gSecret[L][C];
                    penalite++;
                    System.out.println("Pénalité ! Tu as pris un joker !");
                    Check = true;
                }
            }
        }
        return penalite;
    }  // fin  tourHumain

    //.........................................................................

    // Tour de l'ordinateur
    //.........................................................................

    /** pré-requis : gOrdi et nbValPoss sont des matrices 9x9
     *  résultat :   le premier trou (i,j) de gOrdi (c'est-à-dire tel que gOrdi[i][j]==0)
     *               évident (c'est-à-dire tel que nbValPoss[i][j]==1) dans l'ordre des lignes,
     *                s'il y en a, sinon le premier trou de gOrdi dans l'ordre des lignes
     * 
     */
    public static void chercheTrou(int[][] gOrdi,int [][] nbValPoss){
	//___________________________________________________________________
        /*int i = 0;
        int j = 0;
        while (i < gOrdi.length) {
            while (j < gOrdi[i].length) {
                if(gOrdi[i][j] == 0 && nbValPoss[i][j] > 1) {
                    int [] coord = {i,j};
                    push(coord);
                }
                j++;
            }
            j = 0;
            i++;
        }*/
        i = 0;
        j = 0;
        while (i < gOrdi.length) {
            while (j < gOrdi[i].length) {
                if(gOrdi[i][j] == 0 && nbValPoss[i][j] == 1) {
                    int [] coord = {i,j};
                    push(coord);
                }
                j++;
            }
            j = 0;
            i++;
        }
    }  // fin chercheTrou

    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action :     effectue un tour de l'ordinateur      
     */
    public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){
	//________________________________________________________________________________________________
        int penalite = 0;
        if (valremplie == 0) {
            chercheTrou(gOrdi, nbValPoss);
        }
        int [] trouEvident = pull();
        int i = trouEvident[0];
        int j = trouEvident[1];
        int nombre = uneValeur(valPossibles[i][j]);
        if (nbValPoss[i][j] == 1) {
            gOrdi[i][j] = nombre;
            suppValPoss(gOrdi, i, j, valPossibles, nbValPoss);
            System.out.println("Trou évident");
        }
        else if (nbValPoss[i][j] > 1) {
            penalite++;
            System.out.println("Pénalité ! L'ordinateur a pris un joker");
            System.out.println("Saisissez la valeur correcte pour l'ordinateur à la ligne " + (i+1) + " et colonne " + (j+1));
            nombre = saisirEntierMinMax(1,9);
            gOrdi[i][j] = nombre;
        }
        suppValPoss(gOrdi, i, j, valPossibles, nbValPoss);
        return penalite;
    }  // fin tourOrdinateur

    //.........................................................................
    
    // Partie
    //.........................................................................

    /** pré-requis : aucun
     *
     *  action : crée et initialise les matrices utilisées dans une partie, et effectue une partie de Sudoku entre le joueur humain et l'ordinateur. 
     *
     *  résultat :   0 s'il y a match nul, 1 si c'est le joueur humain qui gagne et 2 sinon
     */
    public static int partie(){
	//_____________________________
        int [][] gSecret = new int [9][9];
        int [][] gHumain = new int [9][9];
        int [][] gOrdi = new int [9][9];
        boolean [][][] valPossibles = new boolean [9][9][10];
        int [][] nbValPoss = new int [9][9];
        System.out.println("Bienvenue dans le sudoku, choisissez le nombre de trous");
        int nbTrous = initPartie(gSecret, gHumain, gOrdi, valPossibles, nbValPoss);
        int penaliteHumain = 0;
        int penaliteOrdi = 0;
        afficheGrille(3, gHumain);
        afficheGrille(3, gOrdi);
        for (int i = 0; i < nbTrous; i++) {
            penaliteHumain += tourHumain(gSecret, gHumain);
            afficheGrille(3, gHumain);
            penaliteOrdi += tourOrdinateur(gOrdi, valPossibles, nbValPoss);
            afficheGrille(3, gOrdi);
        }
        if (penaliteHumain == penaliteOrdi) return 0;
        else if (penaliteHumain < penaliteOrdi) return 1;
        else return 2;
    }  // fin partie

    public static void afficherMatrice(int[][] M){
	for (int i=0;i<M.length;i++){
	    for (int j=0;j<M[0].length;j++){
		System.out.print(M[i][j]+" ");
	    }
	    System.out.println();
	}
    }

    //.........................................................................

    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *               et affiche qui a gagné
     */
    public static void main(String[] args){
        int gagnant = partie();
        if (gagnant == 0) System.out.println("C'est un match nul !");
        else if (gagnant == 1) System.out.println("L'humain a gagné !");
        else System.out.println("L'ordinateur a gagné !");
     // fin main
    }
} // fin SudokuBase