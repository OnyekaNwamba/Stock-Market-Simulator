import java.util.*;
import java.io.*;
import java.awt.*;

public class Market{
    // instance variables - replace the example below with your own
    public static TextArea marketArea;
    static HashMap<String,Double> stockPrices;
    static HashMap<String,Double> newStockPrices;
    Label label = new Label();
    private static ArrayList<Stock> stocksMarket;
    /**
     * Constructor for objects of class Market
     */
    public Market() {
        // initialise instance variables
        stocksMarket = new ArrayList<Stock>();
        //Setting up stocks
        TechStock google = new TechStock("Google", "GOOGL", 1127.58);
        TechStock apple = new TechStock("Apple", "APPL", 170.89);
        FashionStock nike = new FashionStock("Nike", "NYSE", 84.71);
        FashionStock adidas = new FashionStock("Adidas","ETR", 201.10);
        BussinessStock amazon = new BussinessStock("Amazon", "AMZN", 1638.01);
        BussinessStock paypal = new BussinessStock("PayPal", "PYPL", 94.38);
        
        stocksMarket.add(0,google);
        stocksMarket.add(1,apple);
        stocksMarket.add(2,nike);
        stocksMarket.add(3,adidas);
        stocksMarket.add(4,amazon);
        stocksMarket.add(5,paypal);

    }
    
    public static ArrayList<Stock> upDown() {
       //Market market = new Market();
       //String content;
       for(int i=0; i<6; i++) {
           Random rand = new Random();    
           Stock stock = stocksMarket.get(i);
           String company = stock.getSymbol();
           double currentValue = stock.getValue();
           int operator = rand.nextInt(2);
           
           if(operator==0) {
               double decrease = Math.round((rand.nextDouble()*30.456));
               double newValue = Math.round(currentValue-decrease);
               stock.setPrice(newValue);
               //content = "<html>" + String.valueOf(company) + ": "  + String.valueOf(newValue) + "( - " + decrease+")</br></html>";
               stocksMarket.set(i, stock);
               //system.out.println(content);
           } else {
               double increase = Math.round((rand.nextDouble()*30.456));
               double newValue = Math.round(currentValue+increase);
               stock.setPrice(newValue);
               //content = "<html>" + String.valueOf(company) + ": "  + String.valueOf(newValue) + "( + " + increase+")</br></html>";
               stocksMarket.set(i, stock);
               //system.out.println(content);
           }
       }
       
       return stocksMarket;
    }
    
    public static void main(String args[]) {
        upDown();
    }
}
