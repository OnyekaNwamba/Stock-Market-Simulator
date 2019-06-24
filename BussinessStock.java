import java.util.*;

public class BussinessStock extends Stock {
    // instance variables - replace the example below with your own
    private String company;
    String symbol;
    private double value;
    String[] events;
   
    /**
     * Constructor for objects of class BussinessClass
     */
    public BussinessStock(String name, String ssymbol, double price ) {
        // initialise instance variables
        super(name,ssymbol,price);
        events  = new String[7];
    }
    
    public String getEvent(String randomCompany) {
        Random rand = new Random();
        int randIndex = rand.nextInt(7);
  
        events[0] = "CEO of " + randomCompany + " has stepped down";
        events[1] = "CEO of " + randomCompany + " has died";
        events[2] = "BTS collaboration with " + randomCompany;
        events[3] = randomCompany + " Stock Faces Headwinds After Strong Gains, Analyst Says";
        events[4] = randomCompany + " loses lawsuit against EU ";
        events[5] =  "PayPal stock downgraded at Buckingham Research on Venmo, eBay concerns";
        events[6] = randomCompany + " bought by Facebook";
        
        String event = events[randIndex];
        
        return event;
    }
}

 
    
