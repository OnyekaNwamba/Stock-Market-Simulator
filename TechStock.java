import java.util.*;

public class TechStock extends Stock {
    // instance variables - replace the example below with your own
    private String company;
    String symbol;
    private double value;
    private String[] events = new String[7];
    /**
     * Constructor for objects of class BussinessClass
     */
    public TechStock(String name,String ssymbol, double price ) {
        // initialise instance variables
        super(name,ssymbol,price);
        events = new String[7];
    }
    
    public String getEvent(String randomCompany) {
        Random rand = new Random();
        int randIndex = rand.nextInt(7);
        
        events[0] = "CEO of " + randomCompany + " has stepped down";
        events[1] = "CEO of " + randomCompany + " has died";
        events[2] = "BTS collaboration with " + randomCompany;
        events[3] = randomCompany + " goes to administration";
        events[4] = randomCompany + " loses lawsuit against EU ";
        events[5] = randomCompany + " looks to regain momentum in smart home market";
        events[6] = randomCompany + " buys Instagram";
        
        String event = events[randIndex];
        
        return event;
    }

}
