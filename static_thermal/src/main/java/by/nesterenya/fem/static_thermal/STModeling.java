package by.nesterenya.fem.static_thermal;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import by.nesterenya.fem.primitives.Box;

import com.jogamp.opengl.util.FPSAnimator;

public class STModeling implements ActionListener {

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
		frame.setBounds(100, 100, 658, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		glDisplay.setRealized(true);
		glDisplay.setMinimumSize(new DimensionUIResource(50, 50));
		glDisplay.setMaximumSize(new DimensionUIResource(400, 400));
		glDisplay.setSize(new DimensionUIResource(200, 200));

		glDisplay.addMouseMotionListener(new MouseMotionAdapter() {
			private double lastY = 0;
			private double lastX = 0;

			@Override
			public void mouseDragged(MouseEvent arg0) {

				if (arg0.isControlDown()) {

					double currentX = arg0.getXOnScreen();
					double currentY = arg0.getYOnScreen();

					double oy = Math.abs(currentY - lastY);
					double ox = Math.abs(currentX - lastX);

					// TODO убрать числовые значения
					if (oy < ox) {

						if (lastX < currentX) {
							glDisplay.getPosition().addToMoveX(0.05);
						} else {
							glDisplay.getPosition().addToMoveX(-0.05);
						}

					} else {
						if (lastY < currentY) {
							glDisplay.getPosition().addToMoveY(-0.05);
						} else {
							glDisplay.getPosition().addToMoveY(0.05);
						}
					}
					lastX = currentX;
					lastY = currentY;

				} else {
					glDisplay.getPosition().setAngle_x(arg0.getX());
					glDisplay.getPosition().setAngle_y(arg0.getY());
				}
			}
		});

		glDisplay.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				glDisplay.getPosition().addToZoom(-0.05 * e.getUnitsToScroll());
			}
		});
		;

		final JSplitPane splitPane = new JSplitPane();

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(20, 1, 2, 5));

		leftPanel.add(new JLabel("Длина пластинки"));
		final JTextField tb_plateLenght = new JTextField();
		leftPanel.add(tb_plateLenght);

		leftPanel.add(new JLabel("Ширина пластинки"));
		final JTextField tb_plateWidth = new JTextField();
		leftPanel.add(tb_plateWidth);

		leftPanel.add(new JLabel("Высота пластинки"));
		final JTextField tb_plateHeight = new JTextField();
		leftPanel.add(tb_plateHeight);

		leftPanel.add(new JLabel("Узлов по OX"));
		JTextField tb_nodeCountOX = new JTextField();
		tb_nodeCountOX.setEnabled(false);
		leftPanel.add(tb_nodeCountOX);

		leftPanel.add(new JLabel("Узлов по OY"));
		JTextField tb_nodeCountOY = new JTextField();
		tb_nodeCountOY.setEnabled(false);
		leftPanel.add(tb_nodeCountOY);

		leftPanel.add(new JLabel("Узлов по OZ"));
		JTextField tb_nodeCountOZ = new JTextField();
		tb_nodeCountOZ.setEnabled(false);
		leftPanel.add(tb_nodeCountOZ);

		JButton btn_disp = new JButton();
		btn_disp.setText("Draw");
		btn_disp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				double box_ox = Double.parseDouble(tb_plateLenght.getText());
				double box_oy = Double.parseDouble(tb_plateWidth.getText());
				double box_oz = Double.parseDouble(tb_plateHeight.getText());

				Box box = new Box(box_ox, box_oy, box_oz);
				glDisplay.setModel(box);
				glDisplay.display();
			}
		});

		leftPanel.add(btn_disp);

		splitPane.setLeftComponent(leftPanel);

		JPanel dispPanel = new JPanel();
		splitPane.setRightComponent(dispPanel);
		dispPanel.setLayout(new BorderLayout(0, 0));
		dispPanel.add(glDisplay);

		frame.getContentPane().add(splitPane);

		animator.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
