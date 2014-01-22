import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;

public class ServerExample {

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
        Request template = new Request();
        template.name = null;
        template.purchased = "no";
        template.status = "requested";
        
        //template.reference = 5;
        //template.venue = "wembley";
        //template.price = 20;
        
        //object
        Request object = new Request();
        object.name = null;
        object.purchased = "no";
        object.status = "requested";
        
        
        int i = 0;
        while(fly != null){
        	
        	if(fly.read(template, LEASE_TIME) == null){
        		System.out.println("Complete");
        		
        	}
        	else{
        		fly.take(template, LEASE_TIME);
                object.purchased = "yes";
                object.status = "allocated";
                fly.write(object, LEASE_TIME);
                i = 1 + i;
                //time delay?
                System.out.println("Server used" + i + object);
        	}
        	//fly.take(template, 500);
            //template.purchased = "yes";
            //template.status = "allocated";
            //fly.write(template, 500);
            //System.out.println("Server used");


	}

}
}
