import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

public class Write {
	final static int LEASE_TIME = 60000;
	final static int TIMER_BUFFER_SIZE = 500;

	static long[] timerBuffer = new long[TIMER_BUFFER_SIZE];
	static int timerIndex = 0;
	
	public static void main(String[] args) {
		
		

		TimerThread timer = new TimerThread();
		timer.start();

		int iterations = 50000;
		// if (args.length > 0) {
		// iterations = Integer.parseInt(args[0]);
		// }

		Fly fly = new FlyFinder().find();

		if (fly == null) {
			System.err
					.println("Failed to find a Fly Server running on the local network");
			System.exit(1);
		}
		// object

		// object.reference = i;
		// object.venue = "wembley";
		// object.price = 20;

		long startTime, stopTime;

		for (int i = 0; i < iterations; i++) {
			Request request = Request.getRequestWithUID();
			request.name = "TicketRequest";
			request.purchased = "no";
			request.status = "available";

			startTime = System.nanoTime();

			fly.write(request, LEASE_TIME);

			String uid = request.uid;

			// System.out.println("Written Request " + uid);

			Response responseTemplate = new Response(uid);

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
			
			// System.out.println("Taken Request " + response.uid + " time = " +
			// roundTrip);

		}
		System.out.println("Objects Written");
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
			long sum = 0;
			for (int i=0; i<TIMER_BUFFER_SIZE;i++) {
				sum += timerBuffer[i];
			}
			System.out.println("Mean Time = " + sum/TIMER_BUFFER_SIZE);
		}
	}
	}

}