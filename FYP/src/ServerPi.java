import java.net.Socket;

import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;


public class ServerPi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int LEASE_TIME = 60000;
		final String HOST = "192.168.0.17";
        final int DEFAULT_PORT = 4396;
        
//		Fly fly = new FlyFinder().find();
//	    
//        if (fly == null) {
//        	System.err.println("Failed to find a Fly Server running on the local network");
//        	System.exit(1);
//        }
		Socket socket = new Socket(HOST, DEFAULT_PORT);
		
		
        Request template = Request.getRequestWithoutUID();
        template.name = null;
        template.purchased = "no";
        template.status = "available";

        while(socket != null){
        	
        	
        	Request request = null;
        	
        	while (request==null) {
        		request = socket.take(template, 0);
        	}
        	
        	String uid = request.uid;
        	
        	Response response = new Response(uid);
        	response.name = "TicketRequest";
			response.purchased = "yes";
			response.status = "purchased";

        	socket.write(response, LEASE_TIME);
        	

	}
	}

}
