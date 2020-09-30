package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.DataWriter;

/**
 * Der zugehörige Controller zur MainFrame.
 * @author Bruno Kreyßig
 *
 */
public class Controller implements ActionListener, ChangeListener{

	// ActionCommand-Konstanten
	public static final String ROTATE = "rotate";
	public static final String EXPORT = "export";
	public static final String THRESHOLD_CHECKBOX = "check_Thresh";
	public static final String INVERT_CHECKBOX = "check_Invert";
	
	private MainFrame mf;
	
	public Controller(MainFrame _mf) {
		this.mf = _mf;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		switch(command) {
		
			case Controller.ROTATE:
				rotateImage();
				break;
				
			case Controller.EXPORT:
				exportVector();
				break;
				
			case Controller.THRESHOLD_CHECKBOX:
				toggleThreshold();
				break;
				
			case Controller.INVERT_CHECKBOX:
				toggleInvert();
				break;
			
			default:
				break;
		
		
		}
		
		
		
	}
	
	private void toggleInvert() {
		mf.img.useInvertColors = mf.invertCheck.isSelected();
		mf.img.repaint();
	}

	private void toggleThreshold() {
		mf.img.useThreshold = mf.thresholdCheck.isSelected();
		mf.img.repaint();
	}

	private void exportVector() {

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Export Vector");
		
		if (chooser.showSaveDialog(this.mf) == JFileChooser.APPROVE_OPTION) {
			DataWriter writer = DataWriter.getInstance();
			writer.writeData(Arrays.toString(mf.u), chooser.getSelectedFile().getAbsolutePath());
		}
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
				
		mf.img.baseColor = mf.slider.getValue();
		mf.img.repaint();
		
	}

	private void rotateImage() {
		
		int rot = this.mf.img.rotation;		
		this.mf.img.rotation = (rot == 270) ? 0 : rot + 90;
		
		this.mf.img.repaint();
		
	}



	
	
	
}
