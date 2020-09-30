package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JComponent;

/**
 * Grafikkomponente zur Umwandlung des Lösungsvektors u in eine Grafik
 * @author Bruno Kreyßig
 *
 */
@SuppressWarnings("serial")
public class ImageComponent extends JComponent{

	public static final int STANDARD_BASE_COLOR = 100;
	
	public MainFrame mf;
	
	// State Variables
	public int rotation;
	public int baseColor;
	public boolean useThreshold;
	public boolean useInvertColors;
	
	public double vectorMin;
	
	
	public ImageComponent(MainFrame _mf) {
		this.mf = _mf;
		this.rotation = 0;
		this.baseColor = ImageComponent.STANDARD_BASE_COLOR;
		this.useThreshold = false;
		
		this.vectorMin = calculateVectorMin();
		
	}
	
	
	private double calculateVectorMin() {
		
		double[] u_sort = mf.u.clone();
		Arrays.sort(u_sort);
		
		return u_sort[0];		
	}


	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < mf.size; i++) {
			for (int j = 0; j < mf.size; j++) {
				int colorValue = calculateColorValue(i,j);
				g.setColor(new Color(colorValue, colorValue, colorValue));
				g.fillRect(j * mf.pxSize, i * mf.pxSize, mf.pxSize, mf.pxSize);
			}
		}	
		super.paint(g);			
	}


	private int calculateColorValue(int i, int j) {
		
		int returnValue;
		double factor = 0;
		
		// Auswertung des Rotationszustands
		switch (this.rotation) {
			case 0:
				factor = mf.u[mf.size * i + j];
				break;
			case 90:
				factor = mf.u[mf.size * (mf.size - 1 - j) + i];
				break;
			case 180:
				factor = mf.u[mf.size * (mf.size - 1 - i) + (mf.size - 1 - j)];
				break;
			case 270:
				factor = mf.u[mf.size * j + (mf.size - 1 - i)];
				break;
			default:
				break;
		}
		
		// Ist die Schwellwertoption ausgewählt?
		if (useThreshold) {
			if (factor < 0.2) {factor = 0;};
		} else {
			factor += Math.abs(this.vectorMin);
		}		
		
		returnValue = (int) (factor * this.baseColor);
		
		// Falls doch mal zu große Werte durchkommen, dann werden diese im 8Bit-Farbraum gehalten
		if (returnValue > 255) { returnValue = 255;	};
		if (returnValue < 0) { returnValue = 0; };
		
		// Sollen die Farben gekippt werden (also dunkle Pixel hell und helle Pixel dunkel)
		if (useInvertColors) {
			returnValue = 255 - returnValue;
		}
		
		return returnValue;
	}
}
