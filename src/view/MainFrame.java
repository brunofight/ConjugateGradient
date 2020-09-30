package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * Hauptrahmenfenster der Anwendung.
 * @author Bruno Kreyﬂig
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	public double[] u;
	public int size;
	
	public int pxSize;
	
	public ImageComponent img;
	public JSlider slider;
	public JCheckBox thresholdCheck;
	public JCheckBox invertCheck;
	
	public MainFrame(double[] _u, int _size, int _pxSize) {
		this.u = _u;
		this.size = _size;
		this.pxSize = _pxSize;
	}
	
	public void initialise(Controller c, int iter, long duration, double normR) {
		
		this.setTitle("CGLS");

		this.setLayout(new BorderLayout());
		
		this.img = new ImageComponent(this);
		this.add(this.img, BorderLayout.CENTER);
		
		JPanel controlsPanel = buildControlsPanel(c);
		this.add(controlsPanel, BorderLayout.SOUTH);
		
		this.setSize( pxSize * this.size + 5, pxSize * this.size + 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		showAlgorithmInfo(iter, duration, normR);
	}

	private void showAlgorithmInfo(int iter, long duration, double normR) {
		String infoText = String.format("CGLS Algorithm terminated after %s iterations.\nCalculated within %.3fs.\n|r_%s| = %s", iter, (float) duration / 1000, iter, normR);
		JOptionPane.showMessageDialog(this, infoText, "CGLS success", JOptionPane.INFORMATION_MESSAGE);
	}

	private JPanel buildControlsPanel(Controller c) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		panel.add(buildUpperPanel(c));
		panel.add(buildCenterPanel(c));
		panel.add(buildLowerPanel(c));
		return panel;
	}

	private JPanel buildCenterPanel(Controller c) {
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel label = new JLabel("Use Threshold: ");
		panel.add(label);
		
		this.thresholdCheck = new JCheckBox();
		this.thresholdCheck.setActionCommand(Controller.THRESHOLD_CHECKBOX);
		this.thresholdCheck.addActionListener(c);
		panel.add(thresholdCheck);
		
		JLabel label2 = new JLabel("Flip Colors: ");
		panel.add(label2);
		
		this.invertCheck = new JCheckBox();
		this.invertCheck.setActionCommand(Controller.INVERT_CHECKBOX);
		this.invertCheck.addActionListener(c);
		panel.add(invertCheck);
		
		return panel;
	}

	private JPanel buildLowerPanel(Controller c) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		panel.add(createButton("Rotate", Controller.ROTATE, c));
		panel.add(createButton("Export Vector", Controller.EXPORT, c));
		return panel;
	}

	private JButton createButton(String label, String actionCommand, Controller c) {
		JButton button = new JButton(label);
		button.setActionCommand(actionCommand);
		button.addActionListener(c);
		return button;
	}

	private JPanel buildUpperPanel(Controller c) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel sliderLabel = new JLabel("Lightness:");
		panel.add(sliderLabel);
		
		this.slider = new JSlider(0, 255);
		this.slider.addChangeListener(c);
		panel.add(this.slider);
		
		return panel;
	}
	
}
