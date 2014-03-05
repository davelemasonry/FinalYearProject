import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ESgui extends JFrame implements ActionListener {

	private JButton goButton;
	private JLabel label1;
	private JSlider slider1;
	private JTextArea textArea;
	private PrintStream standardOut;

	public ESgui() {
		this.setTitle("Enterprise System Demonstrator");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		goButton = new JButton("Choose The Quantity Of Tickets And Click Here");

		textArea = new JTextArea(10, 20);
		textArea.setEditable(false);

		PrintStream printStream = new PrintStream(new CustomOutputStream(
				textArea));
		standardOut = System.out;
		System.setOut(printStream);
		System.setErr(printStream);

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
		JScrollPane scrollPane = new JScrollPane(textArea);

		// textArea.add(scrollPane);
		// this.add(label1, BorderLayout.NORTH);
		this.add(slider1, BorderLayout.NORTH);
//		this.add(goButton, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);

		//goButton.addActionListener(this);

	}

//	public void actionPerformed(ActionEvent event) {
//		// TODO Auto-generated method stub
//		writeRun();
//
//	}

	public void writeRun() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				int number = slider1.getValue();
				WriteSlider.iterations = number;
				WriteSlider.main(null);
				// TODO Auto-generated method stub

			}

		});
		thread.start();
		// int number = slider1.getValue();
		// Write.iterations = number;
		// Write.main(null);

		// TestGuiTwo th = new TestGuiTwo();
		// th.setVisible(true);
	}

	private void slider1StateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			WriteSlider.finishWrite = true;
			try {
				Thread.sleep(4 * 1000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WriteSlider.iterations = source.getValue();
			dispose();
			WriteSlider.main(null);
			ESguiFirst tg = new ESguiFirst();
			tg.setVisible(true);
		}
	}

	// TODO add your code here

	public static void main(String[] args) {
		ESgui tg = new ESgui();
		tg.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
