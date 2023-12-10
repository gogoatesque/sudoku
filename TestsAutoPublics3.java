
// Placer ce fichier dans le même dossier que SudokuBase.java et les autres .java, et lancer le main de ce fichier : javac *.java ; java TestsAutoPublics

// Date : 10/12/2023

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.*;

import java.util.Arrays; 

public class TestsAutoPublics3 {
 
    public static void main(String[] args) {

	double note = 0;
	
	// Ici on lance les tests, si l'appel aux méthodes concernées déclenche une exception ou fait une boucle infinie, pas de pb, on passera qd même au suivant.
	
        note += runTest(TestsAutoPublics3::testCopieMatrice, "testCopieMatrice",1); // Le dernier paramètre est le barême du test.
        note += runTest(TestsAutoPublics3::testEnsPlein, "testEnsPlein",1);
	note += runTest(TestsAutoPublics3::testSupprime, "testSupprime",1);
	note += runTest(TestsAutoPublics3::testUneValeur, "testUneValeur",1);
	note += runTest(TestsAutoPublics3::testDebCarre, "testDebCarre",1);
	note += runTest(TestsAutoPublics3::testInitGrilleComplete, "testInitGrilleComplete",1);
	note += runTest(TestsAutoPublics3::testInitGrilleIncomplete, "testInitGrilleIncomplete",1);

	note += runTest(TestsAutoPublics3::testInitPleines, "testInitPleines",1);
	note += runTest(TestsAutoPublics3::testSuppValPoss, "testSuppValPoss",1);
	note += runTest(TestsAutoPublics3::testInitPossibles, "testInitPossibles",1);
	note += runTest(TestsAutoPublics3::testChercheTrou1, "testChercheTrou1",1);
	// note += runTest(TestsAutoPublics3::testChercheTrou2, "testChercheTrou2",1);
        
	System.out.println("fin des tests : note = " + note);
       
    }


    public static double runTest(Callable<Double> r, String s, int bareme){
	ExecutorService executorService = Executors.newSingleThreadExecutor(); //si on submit à nouveau sans re créer ça timeout aussi pour tests suivants
	Future<Double> future = executorService.submit(r);
	double note = 0;
	try {
	     
	    note+= future.get(1L, TimeUnit.SECONDS)*bareme; //get renvoie entre 0 et 1
	    System.out.println("****************************************************");
	    System.out.println(s + " terminé, note: " + note + "/" + bareme);
	    System.out.println("****************************************************");
	} catch (TimeoutException e) {
	    System.out.println("****************************************************");
	    System.out.println(s + " timeout");
	    System.out.println("****************************************************");
	} catch (InterruptedException | ExecutionException e) {
	    System.out.println("****************************************************");
	    System.out.println(s + " erreur " + e.getMessage());
	    System.out.println("****************************************************");
        }
	executorService.shutdownNow();
	return note;
    }

    
    private static double testCopieMatrice() {

	int[][] m1 = { {1,1},
		       {2,2},
		       {3,3} };
	int[][] m2 = { {6,6},
		       {6,6},
		       {6,6} };
	SudokuBase.copieMatrice(m1,m2);
	for (int i = 0 ; i < m1.length ; i++) {
	    for (int j = 0 ; j < m1[0].length ; j++) {
		if (m1[i][j] != m2[i][j]) return 0;
	    }
	}
	return 1;	
    }

    private static double testEnsPlein() {
	boolean [] res = SudokuBase.ensPlein(12);
	
	if (res.length != 13) return 0;
	
	for(int k = 1; k < res.length; k++)
	    if ( ! res[k] ) return 0;
	
	return 1;
    }

    private static double testSupprime() {
	boolean [] ens = {true, false, false, true};
	boolean res = SudokuBase.supprime(ens, 3);
	if (! res) return 0;
	if (ens[3]) return 0;
	return 1;
    }	

    private static double testUneValeur() {
	boolean [] ens = {true, false, false, true};
	int res = SudokuBase.uneValeur(ens);
	if (res == 3)
	    return 1;
	else
	    return 0;
    }

    private static double testDebCarre() {
	int [] coords = SudokuBase.debCarre(3,5,7);
	if ( coords[0] == 3 && coords[1] == 6)
	    return 1;
	else
	    return 0;
    }

    private static double testInitGrilleComplete() {
	int [][] g = new int[9][9];
	SudokuBase.initGrilleComplete(g);
	for (int [] ligne : g) {
	    for (int val : ligne) {
		if (val <= 0) return 0;
	    }
	}
	return 1;
    }

    private static double testInitGrilleIncomplete() {
	int [][] gc = { {1,2,3,4,5,6,7,8,9},
			{4,5,6,7,8,9,1,2,3},
			{7,8,9,1,2,3,4,5,6},
			{2,3,4,5,6,7,8,9,1},
			{5,6,7,8,9,1,2,3,4},
			{8,9,1,2,3,4,5,6,7},
			{3,4,5,6,7,8,9,1,2},
			{6,7,8,9,1,2,3,4,5},
			{9,1,2,3,4,5,6,7,8} };
	int [][] gi = new int[9][9];

	SudokuBase.initGrilleIncomplete(73,gc,gi);

	int nbTrous = 0;
	for (int [] ligne : gi) {
	    for (int val : ligne) {
		if (val == 0) nbTrous++;
	    }
	}
	if (nbTrous == 73)
	    return 1;
	else
	    return 0;
    }
	
    //.................................................

    private static double testInitPleines() {

	int [][] gOrdi = { {1,2,3,4,5,6,0,0,0},
			   {4,5,6,7,8,9,0,0,0},
			   {7,8,9,1,2,3,0,0,0},
			   {2,3,4,5,6,7,0,0,0},
			   {5,6,7,8,9,1,0,0,0},
			   {8,9,1,2,3,4,0,0,0},
			   {0,0,0,0,0,0,0,0,0},
			   {0,0,0,0,0,0,0,0,0},
			   {0,0,0,0,0,0,0,0,0} };

        boolean[][][] valPossibles = new  boolean[9][9][10]; 
	int [][] nbValPoss = new int[9][9];
	SudokuBase.initPleines(gOrdi, valPossibles, nbValPoss);

	if(!valPossibles[0][6][3] | nbValPoss[0][6] != 9)
	    return 0;

	if(!valPossibles[8][8][9] | nbValPoss[8][8] != 9)
	    return 0;

	return 1;
    }

//.................................................


    private static double testSuppValPoss() {

        int [][] gOrdi = new int[9][9];
	boolean[][][] valPossibles = new  boolean[9][9][10]; 
	int [][] nbValPoss = new int[9][9];

        for(int i=0;i<9;i++)
	    for(int j=0;j<9;j++){
		gOrdi[i][j]=0;     
                valPossibles[i][j][8] = true;          
                nbValPoss[i][j] = 1;
	    }  

	gOrdi[1][4] = 8;                  

	SudokuBase.suppValPoss(gOrdi,1,4,valPossibles, nbValPoss);
       
	if ( valPossibles[1][7][8]  | nbValPoss[1][7] != 0 |
	     valPossibles[5][4][8]  | nbValPoss[5][4] != 0 |
	     valPossibles[2][5][8]  | nbValPoss[2][5] != 0 )
	    return 0;  

	if ( !valPossibles[0][6][8]  | nbValPoss[0][6] != 1 |
	     !valPossibles[3][5][8]  | nbValPoss[3][5] != 1 |
	     !valPossibles[8][8][8]  | nbValPoss[8][8] != 1 )
	    return 0;         
       
	return 1;
    }

//.................................................

    private static double testInitPossibles() {


        int [][] gOrdi = new int[9][9];
	boolean[][][] valPossibles = new  boolean[9][9][10]; 
	int [][] nbValPoss = new int[9][9];

        for(int i=0;i<9;i++)
	    for(int j=0;j<9;j++)
		gOrdi[i][j]=0;     

	gOrdi[1][4]=8;                  

	SudokuBase.initPossibles(gOrdi,valPossibles, nbValPoss);


	if(valPossibles[1][7][8]  | nbValPoss[1][7] !=8 |
           valPossibles[5][4][8]  | nbValPoss[5][4] !=8 |
           valPossibles[2][5][8]  | nbValPoss[2][5] !=8 )
	    return 0;  

	if(!valPossibles[0][6][8]  | nbValPoss[0][6] !=9 |
           !valPossibles[3][5][8]  | nbValPoss[3][5] !=9 |
           !valPossibles[8][8][8]  | nbValPoss[8][8] !=9)
	    return 0;         

	return 1;
    }
//.................................................

    private static double testChercheTrou1() {

     int [][] gOrdi = { {1,2,3,4,5,6,0,0,0},
			{4,5,6,7,8,9,0,0,0},
			{7,8,9,1,2,3,0,0,0},
			{2,3,4,5,6,7,0,0,0},
			{5,6,7,8,9,1,0,0,0},
			{8,9,1,2,3,4,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0},
                        {0,0,0,0,0,0,0,0,0} };
	
	int [][] nbValPoss = new int[9][9];

        for(int i=0;i<9;i++)
	    for(int j=0;j<9;j++)
                 nbValPoss[i][j] = 9;  
 
        nbValPoss[6][4] = 1;          
        nbValPoss[6][5] = 1;          
              
        int[] t = SudokuBase.chercheTrou(gOrdi,nbValPoss);

        if (t[0] !=6 || t[1] != 4)
          return 0;

       return 1;
    }

//.................................................

	
} // end class
