
public class ResponseTime {

	public String uid;
    public String name;
    public Integer reference;
    public String venue;
    public Long price;
    public String purchased;
    public String status;
    public String time;
    
    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("Time: ");
        sb.append(time);
        sb.append("Name :");
        sb.append(name);
        sb.append("\n");
        //sb.append("Reference :");
        //sb.append(reference.toString());
        //sb.append("\n");
        //sb.append("Venue: ");
        //sb.append(venue);
        //sb.append("\n");
        //sb.append("Price: ");
        //sb.append(price.toString());
        //sb.append("\n");
        sb.append("Status:");
        sb.append(status);
        sb.append("\n");
        sb.append("Purchased: ");
        sb.append(purchased);
        sb.append("\n");

        return sb.toString();
    }

}
