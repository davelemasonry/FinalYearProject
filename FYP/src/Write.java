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
	public static long sum;


	public static void main(String[] args) {

		TimerThread timer = new TimerThread();
		timer.start();

		int iterations = 50000;

		Fly fly = new FlyFinder().find();

		if (fly == null) {
			System.err
			.println("Failed to find a Fly Server running on the local network");
			System.exit(1);
		}

		long startTime, stopTime;

		for (int i = 0; i < iterations; i++) {
			Request request = Request.getRequestWithUID();
			request.name = "TicketRequest";
			request.purchased = "no";
			request.status = "available";

			startTime = System.nanoTime();
			fly.write(request, LEASE_TIME);

			String uid = request.uid;

			Response responseTemplate = new Response(uid);
			responseTemplate.name = "TicketRequest";
			responseTemplate.purchased = "yes";
			responseTemplate.status = "purchased";

			Response response = null;

			while (response == null) {
				response = fly.take(responseTemplate, 0);
			}

			stopTime = System.nanoTime();

			long roundTrip = stopTime - startTime;

			if (timerIndex >= TIMER_BUFFER_SIZE) {
				timerIndex = 0;
			}
			timerBuffer[timerIndex++] = roundTrip;




		}
		System.out.println("Objects Written and Received");

		System.out.println(sum);
		timer.finish();

	}
	private static class TimerThread extends Thread {

		boolean stop = false;

		public void finish() {
			stop = true;
		}

		public void run() {
			while(!stop) {
				try {
					Thread.sleep(3*1000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

				for (int i=0; i<TIMER_BUFFER_SIZE;i++) {
					sum += timerBuffer[i];
				}
				sum = sum/100;
				sum = sum/TIMER_BUFFER_SIZE;
				System.out.println("Mean Time = " + sum);
				try{
					File file = new File("graph.txt");
					if(!file.exists()){
						file.createNewFile();

					}
					FileWriter fileWritter = new FileWriter(file.getName(),false);
					
					BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					String sums = String.valueOf(sum);
					bufferWritter.write(sums);
					bufferWritter.newLine();
					bufferWritter.close();

				}catch(IOException e){
					e.printStackTrace();
				}

			}
		}
	}

}