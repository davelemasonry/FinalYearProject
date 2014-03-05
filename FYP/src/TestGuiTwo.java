import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestGuiTwo extends TestGui {

	private JButton goButton, graphButton;
	private JLabel label1;
	private JSlider slider1;

	String value;
	String total;

	public TestGuiTwo() {

		this.setTitle("Enterprise System Demonstrator");
		this.setSize(800, 200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		goButton = new JButton("Choose And Click To Go Again");
		graphButton = new JButton("          Create Graph          ");

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
		label1.setAutoscrolls(true);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setText("Average Of Every 500 Transactions From "
				+ Write.iterations + " iterations: " + value + " milliseconds."
				+ " Total Time: " + total + " seconds");

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
		this.add(graphButton, BorderLayout.WEST);
		this.add(goButton, BorderLayout.EAST);
		this.add(label1, BorderLayout.NORTH);

		goButton.addActionListener(this);
		graphButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent event) {
		JButton clicked = (JButton) event.getSource();
		if (clicked == goButton) {
			int number = slider1.getValue();
			Write.iterations = number;
			dispose();
			File file = new File("average.txt");
			file.delete();
			Write.main(null);
			TestGuiTwo th = new TestGuiTwo();
			th.setVisible(true);
		} else if (clicked == graphButton) {
			dispose();
			TestGuiThree.main(null);
			JFreeSeriesLineChart.main(null);
		}
	}

	private void slider1StateChanged(ChangeEvent e) {
		// TODO add your code here
	}

	public static void main(String[] args) {
		TestGuiTwo tg = new TestGuiTwo();
		tg.setVisible(true);

	}

}
