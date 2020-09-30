package main;

import java.io.FileWriter;
import java.io.IOException;
/**
 * Diese Klasse enth‰lt die notwendige Funktionalit‰t zum Schreiben in eine Ausgabedatei.
 * Das Singletonmuster wurde angewendet weil wir es gerade im Fach Softwaretechnik gelernt hatten.
 * @author Bruno Kreyﬂig
 *
 */
public class DataWriter {

	private static DataWriter instance;
	
	private DataWriter() {}
	
	public static DataWriter getInstance() {
		
		if (DataWriter.instance == null) {
			DataWriter.instance = new DataWriter();
		}
		
		return DataWriter.instance;
		
	}
	
	/**
	 * 
	 * @param s Ausgabetext
	 * @param fileName Ausgabedatei
	 */
	public void writeData(String s, String fileName) {
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(fileName);
			
			
			writer.write(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
