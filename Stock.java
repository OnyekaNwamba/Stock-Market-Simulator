public abstract class Stock {
    // instance variables - replace the example below with your own
    private String company;
    String symbol;
    private double value;

    public Stock(String company, String symbol, double value) {
        this.company = company;
        this.symbol = symbol;
        this.value = value;
    }
    
    public String getName() {
        return company;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public double getValue() {
        return value;
    }
   
    public void setPrice(double vvalue) {
        value = vvalue;
    }
    
    public String getEvent(String randomCompany) {
        return randomCompany;
    }
}

