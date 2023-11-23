import java.util.*;
import java.lang.*;

public class SudokuBase {

    private static Scanner scanner = new Scanner(System.in);
    //.........................................................................
    // Fonctions utiles
    //.........................................................................


    /** pré-requis : min <= max
     *  résultat :   un entier saisi compris entre min et max, avec re-saisie éventuelle jusqu'à ce qu'il le soit
     */
    public static int saisirEntierMinMax(int min, int max){
	//________________________________________________________
        int n = scanner.nextInt();
        while (n < min || n > max) {
            System.out.print("Saisir une valeur correcte :");
            n = scanner.nextInt();
        }
        return n;
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

    /** pré-requis :  n >= 0
     *  résultat : un tableau de booléens représentant le sous-ensemble de l'ensemble des entiers 
     *             de 1 à n égal à lui-même 
     */
    public static boolean[] ensPlein(int n){
	//_____________________________________
        boolean[] TBool = new boolean[n+1];
        for (int i = 0; i <= n; i++){
            TBool[i] = true;
        }
        return TBool;
    }  // fin ensPlein

    /* ENSPLEIN 2 NON FONCTIONNEL
        public static boolean[] ensPlein(int n){
	//_____________________________________
        boolean[] TBool = new boolean[n];
        int[] TInt = new int[n];
        for (int i = 0; i < n; i++){
            TInt[i] = i+1;
        }
        for (int i = 0; i < n; i++) {
            TBool[i] = TInt[i] == n;
        }
        return TBool;
    }  // fin ensPlein
    //.........................................................................


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


    /** pré-requis : l'ensemble représenté par ens n'est pas vide
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
        System.out.print("   ");
        for (int i=1; i <= k*k; i++) {System.out.print(i + " ");}
        System.out.println();
        for (int i = 0; i<= k*k*2+3; i++) {System.out.print("-");}
        System.out.println();
        for (int i = 0; i < g.length; i++) {
            System.out.print((i+1) + " |");
            for (int j = 0; j < g[i].length; j++) {
                if ((j+1)%k == 0) System.out.print(g[i][j] + "|");
                else System.out.print(g[i][j] +" ");
            }
            System.out.println();
            if ((i+1)%k == 0) {
                for (int j = 0; j<= k*k*2+3; j++) {System.out.print("-");}
                System.out.println();
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
        return null;
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
           
    }  // fin saisirGrilleIncomplete

    //.........................................................................



    /** pré-requis : gOrdi est une grille de Sudoku incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action : met dans valPossibles l'ensemble des entiers de 1 à 9 pour chaque trou de gOrdi
     *           et leur nombre dans nbValPoss       
     */
    public static void initPleines(int [][] gOrdi, boolean[][][] valPossibles, int [][] nbValPoss){
	//________________________________________________________________________________________________

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

    }  // fin suppValPoss


    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     * action :      met dans valPossibles l'ensemble des valeurs possibles de chaque trou de gOrdi
     *               et leur nombre dans nbValPoss       
     */
    public static void initPossibles(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){
	//________________________________________________________________________________________________
  
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
        return 0;
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
        return 0;
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
    public static int[] chercheTrou(int[][] gOrdi,int [][]nbValPoss){
	//___________________________________________________________________
        return null;
    }  // fin chercheTrou

    //.........................................................................

    /** pré-requis : gOrdi est une grille de Sudoju incomplète,
     *               valPossibles est une matrice 9x9 de tableaux de 10 booléens
     *               et nbValPoss est une matrice 9x9 d'entiers
     *  action :     effectue un tour de l'ordinateur      
     */
    public static int tourOrdinateur(int [][] gOrdi, boolean[][][] valPossibles, int [][]nbValPoss){
	//________________________________________________________________________________________________
        return 0;
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
        return 2;
    }  // fin partie

    //.........................................................................


    /** pré-requis : aucun
     *  action :     effectue une partie de Sudoku entre le joueur humain et l'ordinateur
     *               et affiche qui a gagné
     */
    public static void main(String[] args){
    }  // fin main

} // fin SudokuBase