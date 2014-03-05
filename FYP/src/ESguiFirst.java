import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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




import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

public class ESguiFirst extends JFrame implements ActionListener {

	private JButton goButton;
	private JLabel label1;
	private JSlider slider1;
	private JTextArea textArea;
	private PrintStream standardOut;
	
	final static int LEASE_TIME = 60000;
	final static int TIMER_BUFFER_SIZE = 500;

	static long[] timerBuffer = new long[TIMER_BUFFER_SIZE];
	static int timerIndex = 0;
	public static int average = 0;
	public static long sum;
	public static int count = 0;
	public static int iterations;
	public static Fly fly;
	public static long startTime2;
	public static long startTime;
	public static int finishWrite;
	
	final WriteThread write = new WriteThread();
	final TimerThread timer = new TimerThread();
	final ReceiverThread receiver = new ReceiverThread();

	public ESguiFirst() {
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
		this.add(goButton, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);

		goButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		writeRun();

	}

	public void writeRun() {

		
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				int number = slider1.getValue();
				finishWrite = number;
				iterations = number;
				write.start();
				timer.start();
				receiver.start();
				
				// TODO Auto-generated method stub

			}

		});
		thread.start();

	}

	private void slider1StateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {
			iterations = source.getValue();

		}
	}


	public static class WriteThread extends Thread {
		boolean stop = false;

		public void finish() {
			stop = true;
		}
		

		public void run() {

			while (!stop) {
				
				startTime = System.currentTimeMillis();
				System.out.println("this is the iterations"+ iterations);

				for (int i = 0; i < iterations; i++) {
					
					startTime2 = System.currentTimeMillis();
					RequesterTime request = new RequesterTime();
					String ct = String.valueOf(startTime2);
					request.time = ct;
					request.name = "TicketRequest";
					request.purchased = "no";
					request.status = "available";
					

					fly.write(request, LEASE_TIME);

//					double currentPercentage = iterations * 0.9;
//					if (currentPercentage == i) {
//						System.out.println("I was at 90%: " + i);
//						i=1;
//						break;
//					}
					if(finishWrite != iterations){
						finishWrite = iterations;
						System.out.println("they changed, and i is: "+ i);
						i=1;
						break;
						
					}

				}

			}
		}

	}

	public static class TimerThread extends Thread {

		boolean stop = false;

		public void finish() {
			stop = true;
		}

		public void run() {
			while (!stop) {
				try {
					Thread.sleep(4 * 1000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				sum = 0;
				for (int i = 0; i < TIMER_BUFFER_SIZE; i++) {
					sum += timerBuffer[i];
					if (i == 499) {
						count = count + 1;
					}
				}

				sum = sum / TIMER_BUFFER_SIZE;
				average += sum;
				System.out.println("Mean Time = " + sum);
				
				try {
					File file = new File("graph.txt");
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fileWritter = new FileWriter(file.getName(),
							true);

					BufferedWriter bufferWritter = new BufferedWriter(
							fileWritter);
					String sums = String.valueOf(sum);
					bufferWritter.write(sums);
					bufferWritter.newLine();
					bufferWritter.close();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		public static void average() {
			try {
				File file = new File("average.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fileWritter = new FileWriter(file.getName(), false);

				long stopTime2 = System.currentTimeMillis();
				long totalTime = stopTime2 - startTime;
				totalTime = totalTime / 1000;
				System.out.println("That took " + totalTime + " seconds");

				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				average /= count;
				String averages = String.valueOf(average);
				bufferWritter.write(averages);
				bufferWritter.newLine();
				String totalTimes = String.valueOf(totalTime);
				bufferWritter.write(totalTimes);
				bufferWritter.newLine();
				bufferWritter.close();

			} catch (IOException e) {
				e.printStackTrace();

			}

		}

	}

	public static class ReceiverThread extends Thread {

		boolean stop = false;

		public void finish() {
			stop = true;
		}

		public void run() {
//			int sleep = 0;
			while (!stop) {
//				if (iterations > 19999) {
//				sleep = iterations / 10000;
//				try {
//					Thread.sleep(sleep * 1);
//
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}

				ResponseTime responseTemplate = new ResponseTime();
				responseTemplate.time = null;
				responseTemplate.name = "TicketRequest";
				responseTemplate.purchased = "yes";
				responseTemplate.status = "purchased";

				ResponseTime response = null;

				while (response == null) {
					response = fly.take(responseTemplate, 0);

				}
				long stopTime2 = System.currentTimeMillis();
				long startTime = Long.parseLong(response.time);
				long roundTrip = stopTime2 - startTime;

				if (timerIndex >= TIMER_BUFFER_SIZE) {
					timerIndex = 0;
				}
				timerBuffer[timerIndex++] = roundTrip;

			}
				
				

			
		}
	}
	
	
	
	public static void main(String[] args) {
		
		fly = new FlyFinder().find();

		if (fly == null) {
			System.err
					.println("Failed to find a Fly Server running on the local network");
			System.exit(1);
		}
		
		ESguiFirst tg = new ESguiFirst();
		tg.setVisible(true);

	}

}
