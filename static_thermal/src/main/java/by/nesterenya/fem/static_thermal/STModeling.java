package by.nesterenya.fem.static_thermal;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.DimensionUIResource;

import com.jogamp.opengl.util.FPSAnimator;

public class STModeling implements ActionListener{

	/**
	 * OpenGL Viewer
	 */
	final GlDisplay glDisplay = new GlDisplay();
	
	/**
	 * Timer for refresh image in <code> glDisplay </code>
	 */
	final FPSAnimator animator = new FPSAnimator(glDisplay, 20, true);

	/**
	 * Object current window
	 */
	static STModeling window;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		window = new STModeling();
		window.frame.setVisible(true);
		window.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	public STModeling() {
		initialize();
	}
	
	JFrame frame;
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Static thermal modeling");
		frame.setBounds(100,100,658,450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0,1,0,0));
		
		glDisplay.setRealized(true);
		glDisplay.setMinimumSize(new DimensionUIResource(50, 50));
		glDisplay.setMaximumSize(new DimensionUIResource(400, 400));
		glDisplay.setSize(new DimensionUIResource(200, 200));
		
		JButton btn_disp = new JButton();
		btn_disp.setText("Draw");
		btn_disp.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  System.out.print("sdf");
		          glDisplay.display();
		        }
		      });
		
		final JSplitPane splitPane = new JSplitPane();
		
		splitPane.setLeftComponent(btn_disp);
		JPanel dispPanel = new JPanel();
		
		splitPane.setRightComponent(dispPanel);
		dispPanel.setLayout(new BorderLayout(0, 0));
		dispPanel.add(glDisplay);
		
		frame.getContentPane().add(splitPane);
	}
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
