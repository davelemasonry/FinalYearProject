import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

public class Server {

	public static void main(String[] args) {
		//take request
		//edit fields
		//write request back
		
		final int LEASE_TIME = 60000;
		
		Fly fly = new FlyFinder().find();
	    
        if (fly == null) {
        	System.err.println("Failed to find a Fly Server running on the local network");
        	System.exit(1);
        }
        //template
        Request template = Request.getRequestWithoutUID();
        template.name = null;
        template.purchased = "no";
        template.status = "available";
        
        //template.reference = 5;
        //template.venue = "wembley";
        //template.price = 20;
        
       
        
        
        int i = 0;
        while(fly != null){
        	
        	
        	Request request = null;
        	
        	while (request==null) {
        		request = fly.take(template, 0);
        	}
        	
        	String uid = request.uid;
        	
        	Response response = new Response(uid);
        	
        	fly.write(response, LEASE_TIME);
               
                i = 1 + i;
                //time delay?
                System.out.println("Server used" + i + response);
        	
        	//fly.take(template, 500);
            //template.purchased = "yes";
            //template.status = "allocated";
            //fly.write(template, 500);
            //System.out.println("Server used");


	}

}
}
