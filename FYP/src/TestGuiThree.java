import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestGuiThree extends TestGuiTwo {

	private JButton goButton;
	private JLabel label1;
	private JSlider slider1;
	final static int LEASE_TIME = 60000;
	final static int TIMER_BUFFER_SIZE = 500;

	String value;
	String total;

	public TestGuiThree() {

		this.setTitle("Enterprise System Demonstrator");
		this.setSize(600, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		goButton = new JButton("Go Again");

		String file_name = "average.txt";
		try {
			Graphing file = new Graphing(file_name);
			String[] aryLines = file.OpenFile();

			int i;
			for (i = 0; i < aryLines.length; i++) {
				value = aryLines[0];
				total = aryLines[1];
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		label1 = new JLabel();
		label1.setText("Average Of Every 500 Transactions From "
				+ Write.iterations + " iterations: " + value + " milliseconds."
				+ " Total Time: " + total + " seconds");
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

		this.add(slider1, BorderLayout.CENTER);
		this.add(label1, BorderLayout.NORTH);
		this.add(goButton, BorderLayout.SOUTH);

		goButton.addActionListener(this);

	}

	private void slider1StateChanged(ChangeEvent e) {
		// TODO add your code here
	}

	public void actionPerformed(ActionEvent event) {
		int number = slider1.getValue();
		Write.iterations = number;
		dispose();
		File file = new File("average.txt");
		file.delete();
		Write.main(null);
		TestGuiTwo.main(null);

	}

	public static void main(String[] args) {
		TestGuiThree tg = new TestGuiThree();
		tg.setVisible(true);

	}

}
