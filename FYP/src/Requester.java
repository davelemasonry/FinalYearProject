import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;


public class Requester {
	public static void main(String[] args) {

		final int LEASE_TIME = 60000;
		
		int iterations = 50000;
        Fly fly = new FlyFinder().find();
    
        if (fly == null) {
        	System.err.println("Failed to find a Fly Server running on the local network");
        	System.exit(1);
        }
        
        //template
        Request template = new Request();
        template.name = null;
        template.purchased = "no";
        template.status = "available";
        //template.reference = 5;
        //template.venue = "wembley";
        //template.price = 20;
        for (int i = 0; i < iterations; i++) {
        	fly.take(template, LEASE_TIME);
        
        	//object
        	Request object = new Request();
        	object.name = null;
        	object.purchased = "no";
        	object.status = "available";
        	object.status = "requested";
        	fly.write(object, LEASE_TIME);
        	
        }
        System.out.println("Objects Requested");
    
}
}


