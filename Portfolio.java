import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Portfolio{
    // instance variables - replace the example below with your own
    private int sharesOwned;
    private double cashOwned;
    private int price;
    HashMap<String,Integer>myStocks;
    /**
     * Constructor for objects of class Portfolio
     */
    public Portfolio(){
        // initialise instance variables
        cashOwned = 750;
        myStocks = new HashMap<>();
        
        //initilise hashmap
        myStocks.put("GOOGL", 0);
        myStocks.put("APPL", 0);
        myStocks.put("NYSE", 0);
        myStocks.put("ETR", 0);
        myStocks.put("AMZN", 0);
        myStocks.put("PYPL", 0);
 
    }


    public HashMap<String,Integer> getMap() {
        return myStocks;
    }
    
    public HashMap<String,Integer> setMap(HashMap<String,Integer> map) {
        myStocks = map;
        return map;
    }
     
    public int getShares(String company) {
        sharesOwned = myStocks.get(company);
        return sharesOwned;
    }
    
    public double getCash() {
        return cashOwned;
    }
    
    public  void setCash(double cash) {
        cashOwned = cash;
    }
    
    public  void setSharesBuy(String symbol, int sshares) {
        sshares = myStocks.get(symbol) + sshares;
        myStocks.replace(symbol, sshares);
    }
    
    public  void setSharesSell(String symbol, int sshares) {
        sshares =  myStocks.get(symbol) - sshares;
        myStocks.replace(symbol, sshares);
    }
    
}

