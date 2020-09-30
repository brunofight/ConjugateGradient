package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Eine Klasse zum einfachen Einlesen einer Datei und Konvertierung der Eingabedaten in eine Matrix.
 * Das Singletonmuster wurde angewendet weil wir es gerade im Fach Softwaretechnik gelernt hatten.
 * @author Bruno
 *
 */
public class DataReader {

	private static DataReader instance;
		
	private DataReader() {
		
	}
	
	public static DataReader getInstance() {
		
		if (DataReader.instance == null) {
			DataReader.instance = new DataReader();
		}
		
		return DataReader.instance;
		
	}
	
	/**
	 * 
	 * @param file Eingabedatei
	 * @param rows Anzahl der aus der Eingabedatei zu verwendenden Zeilen
	 * @param cols Anzahl der Zeilen in der Eingabedatei, die zu einer Zeile der Matrix zusammengefasst werden sollen.
	 * @return
	 */
	public double[][] readMatrix(String file, int rows, int cols) {
		
		double[][] matrix = null;
		BufferedReader reader = null;
				
		try {
			reader = new BufferedReader(new FileReader(file));
		
			matrix = processMatrixData(reader, rows, cols);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
				
		return matrix;
	}

	private double[][] processMatrixData(BufferedReader reader, int rows, int cols) throws NumberFormatException, IOException {
		
		double[][] matrix = new double[rows][cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = Double.valueOf(reader.readLine());
			}
		}		
		
		return matrix;
	}	
	
	
}
