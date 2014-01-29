import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

public class Server {

	public static void main(String[] args) {
		
		final int LEASE_TIME = 60000;
		
		Fly fly = new FlyFinder().find();
	    
        if (fly == null) {
        	System.err.println("Failed to find a Fly Server running on the local network");
        	System.exit(1);
        }
        Request template = Request.getRequestWithoutUID();
        template.name = null;
        template.purchased = "no";
        template.status = "available";

        while(fly != null){
        	
        	
        	Request request = null;
        	
        	while (request==null) {
        		request = fly.take(template, 0);
        	}
        	
        	String uid = request.uid;
        	
        	Response response = new Response(uid);
        	response.name = "TicketRequest";
			response.purchased = "yes";
			response.status = "purchased";

        	fly.write(response, LEASE_TIME);
        	

        	


	}

}
}
