package main;
import java.util.Arrays;

import view.Controller;
import view.MainFrame;

public class Launcher {
	
	// Durch Änderung der Konstanten kann das Programm für beliebige Eingabemessungen genutzt werden.
	
	// Parameter der Eingabedateien
	public static final int N_SQUARE = 7396;
	public static final int N = 86;
	public static final int EQUATIONS = 2064;
	
	// Namen der Eingabedateien
	public static final String MATRIX_FILE = "CTAN.dat";
	public static final String VECTOR_FILE = "CtbN.dat";

	public static void main(String[] args) {
		
		// Default-Toleranz
		double tol = 0.1;
				
		if (args.length == 1) {
			tol = Double.parseDouble(args[0]);
		}
				
		// Einlesen der gegebenen Daten; CTAE entspricht A; CtbE entspricht b im LGS Au = b
		DataReader reader = DataReader.getInstance();
		double[][] A = reader.readMatrix(Launcher.MATRIX_FILE, Launcher.EQUATIONS, Launcher.N_SQUARE);
		double[] b = reader.readMatrix(Launcher.VECTOR_FILE, 1, Launcher.EQUATIONS)[0];
							
		// u0 Startvektor; ist der Nullvektor
		double[] u = new double[Launcher.N_SQUARE];
							
		// r0 = b - Au0
		double[] r = LinearOps.addVectorVector(b, LinearOps.multVectorNum(LinearOps.multMatrixVector(A, u), -1));
				
		// s0 = Ct * r0
		double[] s = LinearOps.multMatrixVector(LinearOps.transposeMatrix(A), r);
				
		// p0 = s0
		double[] p = s.clone();
		
		// delta = <s0,s0>
		double delta = LinearOps.skalarProd(s, s);
				
		// Die Matrix A_transponiert muss nicht jedes Mal aufs neue berechnet werden.
		double[][] A_transpose = LinearOps.transposeMatrix(A);
				
		// Weitere Größen, die während der Iteration berechnet werden
		double delta2;
		double delta_;
		double alpha;
		double mueh;
		double[] Ap;
				
		double normR = LinearOps.normVector(r);
		int iter = 0;
				
		long duration = System.currentTimeMillis();
				
		// Iterationsvorschrift des CGLS:
		while (normR >= tol) {
					
			System.out.println(String.format("|r_%s| = %.6f", iter, (float) normR));
					
			// Ap
			Ap = LinearOps.multMatrixVector(A, p);
					
			// delta' = <Ap,Ap>
			delta_ = calculateDelta_(Ap);
					
			// alpha = delta / delta'
			alpha = delta / delta_;
					
			// u_k+1 = u_k + alpha*p_k
			u = calculateU(u, alpha, p);
					
			// r_k+1 = r_k - alpha*A*p_k
			r = calculateR(r, alpha, Ap);
					
			// s_k+1 = transpose(A)*r_k+1
			s = calculateS(A_transpose, r);
					
			// delta_k+1 = <s_k+1,s_k+1>
			
			
			delta2 = calculateDelta(s);
			mueh = delta2 / delta;
			
			
			delta = delta2;
					
			// p_k+1 = s_k+1 + mueh*p_k
			p = calculateP(p, s, mueh);
					
			normR = LinearOps.normVector(r);
			iter++;
					
					
								
		}
					
		duration = System.currentTimeMillis() - duration;
				
							
		MainFrame mf = new MainFrame(u, Launcher.N, 5);
		Controller c = new Controller(mf);
				
		mf.initialise(c, iter, duration, normR);
			   
				
				
	}

	private static double[] calculateP(double[] p, double[] s, double mueh) {

		double[] mueh_P = LinearOps.multVectorNum(p, mueh);	
						
		return LinearOps.addVectorVector(s, mueh_P);
				
	}

	private static double calculateDelta(double[] s) {
		return LinearOps.skalarProd(s, s);
	}

	private static double[] calculateS(double[][] A_transpose, double[] r) {
		return LinearOps.multMatrixVector(A_transpose, r);
	}
		
	private static double[] calculateR(double[] r, double alpha, double[] Ap) {
		double[] negAlph_Ap = LinearOps.multVectorNum(Ap, -1 * alpha);
		return LinearOps.addVectorVector(r, negAlph_Ap);
	}

	private static double[] calculateU(double[] u, double alpha, double[] p) {
		double[] alpha_P = LinearOps.multVectorNum(p, alpha);
		return LinearOps.addVectorVector(u, alpha_P);		
	}

	private static double calculateDelta_(double[] Ap) {
		return LinearOps.skalarProd(Ap, Ap);
	}
	
}
