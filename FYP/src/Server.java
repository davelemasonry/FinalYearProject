import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;


public class Server {
	
	public static Fly fly;
	
	public static void main(String[] args) {

		final int LEASE_TIME = 60000;
		fly = new FlyFinder().find();
		
		while(fly == null){
			fly = new FlyFinder().find();
			System.out.println("Trying to find server..");
		}
		
//		if (fly == null) {
//			System.err
//					.println("Failed to find a Fly Server running on the local network");
//			System.exit(1);
//		}
		RequesterTime template = new RequesterTime();
		template.time = null;
		template.name = null;
		template.purchased = "no";
		template.status = "available";

		while (fly != null) {

			RequesterTime request = null;
			String currentTime;

			while (request == null) {
				request = fly.take(template, 0);
			}
			
			currentTime = request.time;
			ResponseTime response = new ResponseTime();
			//System.out.println("This is the request time: " + request.time);
			response.time = currentTime;
			response.name = "TicketRequest";
			response.purchased = "yes";
			response.status = "purchased";

			fly.write(response, LEASE_TIME);

		}

	}
}
