import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestGui extends JFrame implements ActionListener {

	private JButton goButton;
	private JLabel label1;
	private JSlider slider1;

	public TestGui() {
		this.setTitle("Enterprise System Demonstrator");
		this.setSize(500, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		goButton = new JButton("Choose The Quantity Of Tickets And Click Here");

		label1 = new JLabel();
		label1.setText("Enterprise System Demonstrator");
		label1.setHorizontalAlignment(SwingConstants.CENTER);

		slider1 = new JSlider();
		slider1.setMinimum(10000);
		slider1.setMaximum(100000);
		slider1.setLabelTable(slider1.createStandardLabels(10000));
		slider1.setPaintLabels(true);
		slider1.setPaintTicks(true);
		slider1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				slider1StateChanged(e);
			}
		});

		this.add(label1, BorderLayout.NORTH);
		this.add(slider1, BorderLayout.CENTER);
		this.add(goButton, BorderLayout.SOUTH);

		goButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent event) {
		int number = slider1.getValue();
		Write.iterations = number;
		dispose();
		Write.main(null);
		TestGuiTwo th = new TestGuiTwo();
		th.setVisible(true);
	}

	private void slider1StateChanged(ChangeEvent e) {
		// TODO add your code here
	}

	public static void main(String[] args) {
		TestGui tg = new TestGui();
		tg.setVisible(true);

	}

}
