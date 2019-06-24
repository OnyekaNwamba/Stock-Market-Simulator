import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.HashMap.*;
import java.io.*;
public class GUI extends JFrame implements ActionListener{
     JFrame frame = new JFrame("Stock Simulator");
      JButton b1, b2, b3, b4, b5, ib1, ib2;
      private static JPanel p1,p2,p3,p4,p5;
      JPanel list, headList, sharesPanel, stocks;
      JTextField t1, t2, t3, t4;
      JLabel l1, l2, br, label, l3;

      JTextArea eventArea;
      private  static ArrayList<Stock> stocksArray;
      private  Portfolio myPortfolio = new Portfolio();
      private  ArrayList<ArrayList> sharesArray;
      
      Font font;
      String[] events;
      
      JDialog dialog;
      JDialog graphDialog;
      

      GUI() throws IOException {
           stocksArray = new ArrayList<>();
           Market market = new Market();
           font = new Font("serif", Font.PLAIN, 12);
           
           //Setting up stocks
           TechStock google = new TechStock("Google", "GOOGL", 1127.58);
           TechStock apple = new TechStock("Apple", "APPL", 170.89);
           FashionStock nike = new FashionStock("Nike", "NYSE", 84.71);
           FashionStock adidas = new FashionStock("Adidas","ETR", 201.10);
           BussinessStock amazon = new BussinessStock("Amazon", "AMZN", 1638.01);
           BussinessStock paypal = new BussinessStock("PayPal", "PYPL", 94.38);
            
           //Adding stocks information to arraylist
           stocksArray.add(0,google);
           stocksArray.add(1,apple);
           stocksArray.add(2,nike);
           stocksArray.add(3,adidas);
           stocksArray.add(4,amazon);
           stocksArray.add(5,paypal);
            
           p1 = new JPanel();
           p2 = new JPanel();
           p3 = new JPanel();
           p4 = new JPanel(new GridLayout(2,0));
           p5 = new JPanel();

           sharesPanel = new JPanel();
           headList = new JPanel();
           list = new JPanel();
           br = new JLabel("<html></br></html>");
           stocks = new JPanel();
           
           b2 = new JButton("Get Graphical Data");
           b2.addActionListener(this);
           b3 = new JButton("EAST");
           b4 = new JButton("WEST");
           b5 = new JButton("MIDDLE");
           
           l3 = new JLabel(marketEvent(), SwingConstants.CENTER);
           
           //p2.add(b2);
           p4.add(l3);
           
           getPortfolio();
           stocksView();
           cashView();
           buyView();
           listView();
           
           
           
           frame.addWindowListener(new WindowAdapter() {
              public void windowClosing(WindowEvent e) {
                //save portfolio to file when user presses close button
                try {
                    savePortfolio();
                } catch(Exception ex) {
                    e.toString();
                }
              }
            });
 
           //frame
           frame.add(b2, BorderLayout.SOUTH);
           frame.add(p5, BorderLayout.CENTER);

           frame.setSize(700, 700);
           frame.setVisible(true);

      }
      
      
      public static void main(String args[]) throws Exception {
        GUI gui = new GUI();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                   while(true){
                       
                       Thread.sleep(3000);
                       ArrayList<Stock> stocksMarket = Market.upDown();
                       stocksArray = stocksMarket;
                       JTextArea marketArea = new JTextArea(40,40);
                       marketArea.setMargin(new Insets(10,10,10,10));
                       p5.removeAll();
                       for(int i=0; i < 6; i++) {
                           Stock stock = stocksMarket.get(i);
                           marketArea.append(stock.getSymbol() + " : " + stock.getValue() + "\n");
                           marketArea.append("\n");
                        }
                       
                       p5.add(marketArea);
                       p5.revalidate();

                   }
                }   catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        thread.start();
     }
   
      
      public void stocksView() {
         stocks.setLayout(new GridLayout(8,3));
        JLabel br = new JLabel("<html></br></html>");
        for(int i=0; i<6; i++) {
            String company = stocksArray.get(i).getName();
            String symbol = stocksArray.get(i).getSymbol();
            double value = stocksArray.get(i).getValue();
            String content = company + " , " + symbol + " , "+ value;
            label  = new JLabel();
            Label label = new Label(content);
            label.setFont(font.deriveFont(26));
            
            stocks.add(label);
            
        }
        
    }
      
      public  void cashView() {
          JPanel cashPanel = new JPanel();
          cashPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
          l1 = new JLabel();
          JLabel br = new JLabel("<html></br></html>");
          //chage cash later
          double cash = myPortfolio.getCash();
          l1.setText(Double.toString(cash));
          
          cashPanel.add(l1);
          p1.add(cashPanel);
          frame.add(p1, BorderLayout.NORTH);
          frame.setSize(250, 250);
          frame.setVisible(true);
      }
      
      public  void buyView() {
        ib1 = new JButton("BUY");
        ib1.setPreferredSize(new Dimension(190, 35));
        ib1.addActionListener(this);
        ib2 = new JButton("SELL");
        ib2.addActionListener(this);
        ib2.setPreferredSize(new Dimension(190, 35));

        t1=new JTextField("Enter Symbol");  
        t2=new JTextField("Enter Amount");  
        t1.setPreferredSize(new Dimension(120, 35));
        t2.setPreferredSize(new Dimension(120, 35));
        
        //add all buttons and fields to buy / sell shares
        p1.add(t1);
        p1.add(t2);
        p1.add(ib1);
        p1.add(ib2);

        frame.add(p1, BorderLayout.NORTH);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)  {
        if(e.getSource() == ib1) {
                String symbol = t1.getText();
                int amount = Integer.parseInt(t2.getText());
                String response = buy(symbol, amount);
                
                String content1 = "<html><p>You just bought<p>" + amount + " shares of " + symbol;
   
                listView();
                
        } else if(e.getSource() == ib2)  {
                String symbol = t1.getText();
                int amount = Integer.parseInt(t2.getText());
                String response = sell(symbol, amount);
                
                String content1 = "<html><p>You just sold<p>" + amount + " shares of " + symbol;
                //update hashmap
                listView();
        } else if(e.getSource() == b2) {
                graphDialog = new JDialog();
                String l = "Stock information from this program are simulated and do not reflect real life market events";
                String ll = "You can find real life data/graphs of any company by entering their symbol below";
                String lll = "Enter Symbol";
                JTextField symbols = new JTextField();
                JButton bx = new JButton("Get graphs");
                String br = "<html></br></html>";
                
           
                
                JLabel lx = new JLabel(l);
                JLabel llx = new JLabel(ll);
                //JLabel lllx = new JLabel(lllx);
                
                JPanel content = new JPanel();
                content.add(label);
                content.add(symbols);
                content.add(new JLabel("<html></br></html>"));
                
                //graphDialog.add(label);
                //graphDialog.add(symbols);
                //graphDialog.add(new JLabel("<html></br></html>"));
                //graphDialog.add(bx);
                
                graphDialog.add(content);
                graphDialog.setSize(new Dimension(100,300));
                graphDialog.setVisible(true);

        }
    }
    
    public String buy(String company, int toBuy) {  
    
        HashMap<String, Integer> sharesMap = new HashMap<>();
        double cashOwned = myPortfolio.getCash();
        String response="";
        String symbol;
        int i = 0;
        boolean found = false;
        while(i < 6) {
           symbol = stocksArray.get(i).getSymbol();
           if(company.equals(symbol)) {
               found = true;
               break;
           }

           i = i + 1;
       }
       
       if(found == true) {
           double value = stocksArray.get(i).getValue();
           double cost = value * toBuy;
           if(cashOwned < cost) {
              //System.out.println("POOOOOOR"); 
              dialog =  new JDialog();
              JLabel l = new JLabel("You do not have money to buy shares"); 
              dialog.add(l);
              // setsize of dialog 
              dialog.setSize(300, 300); 
  
              // set visibility of dialog 
              dialog.setVisible(true);
           } else {

              cashOwned = cashOwned - cost;
              response = "BOUGHT";
              myPortfolio.setCash(cashOwned);
              myPortfolio.setSharesBuy(company, toBuy);
              dialog =  new JDialog();
              JLabel l = new JLabel("You just bought " + toBuy + " shares of " + company); 
              dialog.add(l);
              // setsize of dialog 
              dialog.setSize(300, 300); 
  
              // set visibility of dialog 
              dialog.setVisible(true);
           }
        }
        HashMap<String,Integer> myStocks = myPortfolio.getMap();
        Iterator<String> iterator = myStocks.keySet().iterator();
       
        while (iterator.hasNext()) {
            String ccompany = iterator.next();
            String content = String.valueOf(ccompany) + ": "  + String.valueOf(myStocks.get(ccompany));
        } 
       
        l1.setText(Double.toString(cashOwned));
        
        return response;
    }
    
    public String sell(String company, int toSell) {  
    
        HashMap<String, Integer> sharesMap = new HashMap<>();
        double cashOwned = myPortfolio.getCash();
        String response="";
        String symbol;
        int i = 0;
        boolean found = false;
        
        
       while(i < 6) {
           symbol = stocksArray.get(i).getSymbol();
           if(company.equals(symbol)) {
               found = true;
               break;
           }

           i = i + 1;
       }
       
       if(found == true) {
           double value = stocksArray.get(i).getValue();
           double cost = value * toSell;
           int shares = myPortfolio.getShares(company);
           System.out.println(company + " : " + shares + "*");
           if(shares < toSell) {
              dialog =  new JDialog();
              JLabel l = new JLabel("You do not have enough shares to sell " + toSell + " shares of " + company); 
              dialog.add(l);
              // setsize of dialog 
              dialog.setSize(100, 100); 
  
              // set visibility of dialog 
              dialog.setVisible(true);
           } else {
              cashOwned = cashOwned + cost;
              response = "SOLD";
              myPortfolio.setCash(cashOwned);
              myPortfolio.setSharesSell(company, toSell);
              dialog =  new JDialog();
              JLabel l = new JLabel("You just sold " + toSell + " shares of " + company); 
              dialog.add(l);
              // setsize of dialog 
              dialog.setSize(100, 100); 
  
              // set visibility of dialog 
              dialog.setVisible(true);
           }
        }
       
        HashMap<String,Integer> myStocks = myPortfolio.getMap();
        Iterator<String> iterator = myStocks.keySet().iterator();
       
        while (iterator.hasNext()) {
            String ccompany = iterator.next();
            String content = String.valueOf(ccompany) + ": "  + String.valueOf(myStocks.get(ccompany));
       } 
       
        l1.setText(Double.toString(cashOwned));
        
        return response;
    }

    public void listView() {
       
       br = new JLabel("<html></br></html>");
       list.removeAll();
       list.setLayout(new GridLayout(10,3));
       br.setText("<html></br></html>");
       list.add(br);
       list.add(br);
       Label l5 = new Label("My Shares");
       list.add(br);
       list.add(l5);
       list.add(br);
      
       HashMap<String,Integer> myStocks = myPortfolio.getMap();
       hashPrinter(myStocks);

       sharesPanel.add(list);
       p4.add(sharesPanel);
       frame.add(p4, BorderLayout.WEST);

    }
    
    public  ArrayList<ArrayList> hashToArr(HashMap<String, Integer> hash) {
        ArrayList<String> companies = new ArrayList(hash.keySet());
    
        ArrayList<Integer> shares = new ArrayList(hash.values());
    
        ArrayList<ArrayList> arr = new ArrayList();
        arr.add(0,companies);
        arr.add(1,shares);
        
        return arr;
    }
    
    public  void hashPrinter(HashMap<String,Integer> myStocks) {
        br = new JLabel("<html></br></html>");
        Iterator<String> iterator = myStocks.keySet().iterator();
  
          
          while (iterator.hasNext()) {
            String company = iterator.next();
            String content = "<html>" + String.valueOf(company) + ":                 "  + String.valueOf(myStocks.get(company)) + "</br></html>";
            JLabel label = new JLabel(content);
            list.add(label);
            list.add(br);
            list.add(br);
            list.add(br);
            
            
        }
    }
    
    public void savePortfolio() throws IOException {
        // create map
        HashMap<String, Integer> map = myPortfolio.getMap();
        
        //create new file or override existing file
        // Create a new file and override when already exists:
        try (Writer output = openWriter("shares.txt")) {
            //writeHeader(output);
            for (Map.Entry<String, Integer> company : map.entrySet()) {
                output.write(formatRow(company));
            }
        }
 
    }
    
     public void getPortfolio() throws IOException {
        HashMap<String, Integer> tempMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("shares.txt")));
            String line = reader.readLine();

            while (line != null) {
                String strArray[] = line.split(",");
                tempMap.put(strArray[0], Integer.parseInt(strArray[1]));
                System.out.println(strArray[0] + " : " + Integer.parseInt(strArray[1]));
                line = reader.readLine();
    
            }
            reader.close();

        }  catch (IOException ex) {
            System.out.println("Problems..");
             System.out.println(ex.toString());
        }
        myPortfolio.setMap(tempMap);
    } 
    
     
 
    
    private static BufferedWriter openWriter(String fileName) throws IOException {
        return openWriter(fileName, false);
    }
 
    private static BufferedWriter openWriter(String fileName, boolean append) throws IOException {
        //add buffering to have better performance!
        return new BufferedWriter(new FileWriter(fileName, append));
    }
 
    private static String formatRow(Map.Entry<String, Integer> company) {
        String row = String.format("%s,%s%n", company.getKey(), company.getValue());
        System.out.print("Adding row: " + row);
        return row;
    }
    
    
    public  String  marketEvent() {
        Random rand = new Random();
        int randIndex = rand.nextInt(5);
        events = new String[7];
        String randomCompanyName = stocksArray.get(randIndex).getName();
        Stock  randomCompany = stocksArray.get(randIndex);
        String event = randomCompany.getEvent(randomCompanyName);
        return event;
    }
}
