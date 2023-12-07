
// Placer ce fichier dans le meme dossier que SudokuBase.java et les autres .java, et lancer le main de ce fichier : javac *.java ; java TestsAutoPublics

// Date : 7/12/2023

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

public class TestsAutoPublics {
 
    public static void main(String[] args) {

	double note = 0;
	
	// Ici on lance les tests, si l'appel aux méthodes concernées déclenche une exception ou fait une boucle infinie, pas de pb, on passera qd même au suivant.
	
        note += runTest(TestsAutoPublics::testCopieMatrice, "testCopieMatrice",1); // Le dernier paramètre est le barême du test.
        note += runTest(TestsAutoPublics::testEnsPlein, "testEnsPlein",1);
	note += runTest(TestsAutoPublics::testSupprime, "testSupprime",1);
	note += runTest(TestsAutoPublics::testUneValeur, "testUneValeur",1);
	note += runTest(TestsAutoPublics::testDebCarre, "testDebCarre",1);
	note += runTest(TestsAutoPublics::testInitGrilleComplete, "testInitGrilleComplete",1);
	note += runTest(TestsAutoPublics::testInitGrilleIncomplete, "testInitGrilleIncomplete",1);
        
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
	
} // end class
