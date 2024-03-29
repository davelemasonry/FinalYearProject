import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Write {

	final static int LEASE_TIME = 60000;
	final static int TIMER_BUFFER_SIZE = 500;

	static long[] timerBuffer = new long[TIMER_BUFFER_SIZE];
	static int timerIndex = 0;
	public static int average= 0;
	public static long sum;
//	public static long totalTime;
	public static int count = 0;
	public static int iterations;
	public static Fly fly;
	public static long startTime2;
	public static long startTime;

	public static void main(String[] args) {

		TimerThread timer = new TimerThread();
		ReceiverThread receiver = new ReceiverThread();
		timer.start();

		iterations = 10000;

		fly = new FlyFinder().find();

		if (fly == null) {
			System.err
					.println("Failed to find a Fly Server running on the local network");
			System.exit(1);
		}

		long stopTime, stopTime2;
		receiver.start();
		startTime = System.currentTimeMillis();

		for (int i = 0; i < iterations; i++) {
			startTime2 = System.currentTimeMillis();
			RequesterTime request = new RequesterTime();
			String ct = String.valueOf(startTime2);
			request.time = ct;
			request.name = "TicketRequest";
			request.purchased = "no";
			request.status = "available";

			fly.write(request, LEASE_TIME);

		}
	

		//System.out.println("Objects Written");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.finish();
		
		// System.out.println("Here");
		receiver.finish();
		TimerThread.average();
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
					//System.out.println("This is the sum:  "+ sum);
					if (i == 499) {
						count = count + 1;
					}
				}
				
				//long total = sum / TIMER_BUFFER_SIZE;
				sum = sum / TIMER_BUFFER_SIZE;
//				System.out.println("After Division: "+sum);
//				System.out.println("This is the count: "+count);
				average += sum;
//				System.out.println("Average: "+average);
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
//				System.out.println("This is the average: "+ average);
//				System.out.println("This is the count: "+ count);
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

			while (!stop) {

				ResponseTime responseTemplate = new ResponseTime();
				responseTemplate.time = null;
				responseTemplate.name = "TicketRequest";
				responseTemplate.purchased = "yes";
				responseTemplate.status = "purchased";

				ResponseTime response = null;

				while (response == null) {
					response = fly.take(responseTemplate, 0);

				}
				// System.out.println(response);
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
}
