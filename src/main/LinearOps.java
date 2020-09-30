package main;

import java.math.BigDecimal;

/**
 * LinearOps ist eine Hilfsklasse zur einfachen Berechnung von Operationen mit Vektoren und Matrizen.
 * Diese Hilfsklasse kam bereits in der Hausaufgabe zum Gradientenverfahren zum Einsatz und wurde für
 * das CGLS-Verfahren auf das Nötigste reduziert.
 * 
 * Somit ist das Einbinden externer Bibliotheken (wie etwa ejml) für den CGLS-Algorithmus nicht notwendig.
 * @author Bruno Kreyßig
 *
 */
public class LinearOps {
	
	/**
	 * 
	 * @param matrix (der Dimension m*n)
	 * @param vector (der Dimension n)
	 * @return Vektor der Dimension m
	 */
	public static double[] multMatrixVector(double[][] matrix, double[] vector) {
		
		if (matrix[0].length != vector.length) {
			return null;
		}
		double[] res = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			double sum = 0;
				
			for (int j = 0; j < matrix[i].length; j++) {
				sum += matrix[i][j] * vector[j];
			}			
			res[i] = sum;
		}
		return res;
	}
	
	/**
	 *  Hinweis: unter Verwendung einer Kombination von multVectorNum(v, num) und addVector(v1,v2) 
	 *  kann die Subtraktion zweier Vektoren vorgenommen werden.
	 * @param v1 (Länge v1 = v2)
	 * @param v2 (Länge v2 = v1)
	 * @return v1 + v2
	 */
	public static double[] addVectorVector(double[] v1, double[] v2) {
		
		if (v1.length != v2.length) {
			return null;
		}
		
		double[] res = new double[v1.length];
		
		for (int i = 0; i < v1.length; i++) {
			res[i] = v1[i] + v2[i];
		}
		
		return res;
	}
	
	/**
	 * Hinweis: unter Verwendung einer Kombination von multVectorNum(v, num) und addVector(v1,v2) 
	 * kann die Subtraktion zweier Vektoren vorgenommen werden.
	 * @param v
	 * @param num
	 * @return v * num
	 */
	public static double[] multVectorNum(double[] v, double num) {
		
		double[] res = new double[v.length];
		
		for (int i = 0; i < v.length; i++) {
			res[i] = v[i] * num;
		}
		
		return res;
	}
	
	/**
	 * Die Länge der Eingabevektoren muss gleich sein
	 * @param v1
	 * @param v2
	 * @return das Skalarprodukt der Vektoren v1 und v2
	 */
	public static double skalarProd(double[] v1, double[] v2) {
		
		if (v1.length != v2.length) {
			return 0;
		}		
		
		double res = 0;
		
		for (int i = 0; i < v1.length; i++) {
			res += v1[i] * v2[i];
		}
		
		return res;
		
	}
	
	/**
	 * 
	 * @param v
	 * @return Die euklidische Vektornorm, bzw. der Betrag des Vektors v
	 */
	public static double normVector(double[] v) {
	    BigDecimal res = new BigDecimal(0);
		
		for (int i = 0; i < v.length; i++) {
			res = res.add(new BigDecimal(v[i] * v[i]));
		}
						
		return Math.sqrt(res.doubleValue());
	}
	
	/**
	 * 
	 * @param matrix
	 * @return die transponierte Matrix
	 */
	public static double[][] transposeMatrix(double[][] matrix) {
		double[][] res = new double[matrix[0].length][matrix.length];
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				res[i][j] = matrix[j][i];
			}
		}
		
		
		return res;
	}
	

		
		
		
	
}
