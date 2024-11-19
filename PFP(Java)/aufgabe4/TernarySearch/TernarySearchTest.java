

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Small ternary search test
 * 
 * @author Georg Dotzler<georg.dotzler@informatik.uni-erlangen.de>
 */
public class TernarySearchTest {
	final double EPSILON = 0.000001;

	double[][] polynom = {{0,1},{0,0,1},{0,-1,0.5},{42,-2,-0.5,1.0/3.0},{128,-30,-0.5,2,0.25},{128,-30,-0.5,2,0.25}};
	double[] solution = {-1,0,1,2,2,-5};
	double[] bordersLeft = {-1,-1,-5,-1,-1,-10};
	double[] bordersRight = {1, 1, 1, 5, 4, -4};

	Function[] getFunctions(){
		Function[] list = new Function[polynom.length];
		for (int i = 0; i < polynom.length; i++){
			list[i] = new Polynomial(polynom[i]);
		}
		return list;
	}


	@Test
	public void testTernarySearch() {
		TernarySearch b = new TernarySearchImpl();
		Function[] f = getFunctions();
		for (int i = 0; i < polynom.length;i++){
			Double result = b.findMinimum(f[i], bordersLeft[i], bordersRight[i]);
			System.err.println("TernarySearch "+i+": "+i+" solutionSequential: "+solution[i]+" result: "+result);
			assertTrue(result!=null);
			assertEquals(solution[i], result, EPSILON);
		}
	}

	@Test
	public void testThreadedSearch() {
		TernarySearch ts = new TernarySearchImpl();
		ParallelTernarySearch b = new ThreadedTernarySearch();
		Function[] f = getFunctions();
		Double[] result = b.findMinimum(f, bordersLeft, bordersRight,4, ts);
		for (int j = 0; j < f.length; j++) {
			System.err.println("ThreadedTernarySearch "+j+": "+j+" solution: "+solution[j]+" result: "+result[j]);
			assertTrue(result[j]!=null);
			assertEquals(solution[j], result[j], EPSILON);
		}
	}

//	@Test
//	public void testTaskedSearch() {
//		TernarySearch ts = new TernarySearchImpl();
//		ParallelTernarySearch b = new TaskedTernarySearch();
//		Function[] f = getFunctions();
//		Double[] result = b.findMinimum(f, bordersLeft, bordersRight,4, ts);
//		for (int j = 0; j < f.length; j++) {
//			System.err.println("TaskedTernarySerach "+j+": "+j+" solution: "+solution[j]+" result: "+result[j]);
//			assertTrue(result[j]!=null);
//			assertEquals(solution[j], result[j], EPSILON);
//		}
//	}
//
//	@Test
//	public void testTernaryThreadedLarge(){
//	        int anzahl = 50000;
//        	Function[] f = new Function[anzahl];
//	        double[] l = new double[anzahl];
//        	double[] r = new double[anzahl];
//	        for (int i = 0; i < anzahl; i++) {
//        	    f[i] = Polynomial.randomPolynomial((int) (Math.random() * 4));
//	            l[i] = -Math.random()*1000;
//        	    r[i] = Math.random()*1000;
//	        }
//        	Double[] result = new Double[anzahl];
//	        ThreadedTernarySearch ts = new ThreadedTernarySearch();
//        	TernarySearch ref = new TernarySearchImpl();
//	        long start, end;
//        	start = System.nanoTime();
//	        result = ts.findMinimum(f, l, r,8,ref);
//	    	end = System.nanoTime();
//        	for (int i = 0; i < anzahl; i++) {
//        		assertTrue(result[i]!=null);
//	        }
//        	System.err.println("Threaded Laufzeit: "+(end-start)/1E9);
//	}
//
//	@Test
//	public void testTaskedThreadedLarge(){
//	        int anzahl = 50000;
//        	Function[] f = new Function[anzahl];
//	        double[] l = new double[anzahl];
//        	double[] r = new double[anzahl];
//	        for (int i = 0; i < anzahl; i++) {
//        	    f[i] = Polynomial.randomPolynomial((int) (Math.random() * 4));
//	            l[i] = -Math.random()*1000;
//        	    r[i] = Math.random()*1000;
//	        }
//        	Double[] result = new Double[anzahl];
//	        TaskedTernarySearch ts = new TaskedTernarySearch();
//        	TernarySearch ref = new TernarySearchImpl();
//	        long start, end;
//        	start = System.nanoTime();
//	        result = ts.findMinimum(f, l, r,8,ref);
//    		end = System.nanoTime();
//	        for (int i = 0; i < anzahl; i++) {
//        		assertTrue(result[i]!=null);
//        	}
//	        System.err.println("Tasked Laufzeit: "+(end-start)/1E9);
//	}
//
}
