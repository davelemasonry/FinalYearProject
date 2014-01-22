import com.zink.fly.Fly;
import com.zink.fly.kit.FlyFinder;


public class Reciever {

	public static void main(String[] args) {
		final int LEASE_TIME = 60000;
		
		Fly fly = new FlyFinder().find();
	    
        if (fly == null) {
        	System.err.println("Failed to find a Fly Server running on the local network");
        	System.exit(1);
        }
        
        //template
        Request template = new Request();
        template.name = null;
        template.purchased = "yes";
        template.status = "allocated";
        //template.reference = 5;
        //template.venue = "wembley";
        //template.price = 20;
        int i = 0;
        while(fly != null){
        	
        	if(fly.read(template, LEASE_TIME) == null){
        		System.out.println("Complete");
        	}
        	else{
        		fly.take(template, LEASE_TIME);
                i = 1 + i;
                System.out.println("Ticket Bought" + i + template);
        	}
        
        

	}

}
}
