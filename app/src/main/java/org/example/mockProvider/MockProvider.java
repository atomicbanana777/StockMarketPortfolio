package org.example.mockProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

import org.example.portfolio.interfaceClass.DataProvider;

public class MockProvider implements DataProvider, Runnable{
    private HashMap<String, Stock> stocks = new HashMap<>();
    private static final String csvFile = "mock.csv";
    private URL resourceURL;

    private final int sleepTime = 1000;

    public void start(){
        load();
        new Thread(this).start();
    }

    @Override
    public BigDecimal getStockPrice(String ticker) {
        return stocks.get(ticker).getCurr_price();
    }

    public String getCSVFile(){
        return csvFile;
    }

    public void setResourceURL(URL resourceURL){
        this.resourceURL = resourceURL;
    }

    public void load(){
        File file = new File(csvFile);
        if(file.exists()){
            try {
                setResourceURL(file.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if(resourceURL != null){
            try(InputStream is = resourceURL.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));){
                    boolean firstLine = true;
                    String line = br.readLine();
                    while (line != null) {
                        if(!firstLine){ //skip 1st line
                            String[] str = line.split(",");
                            Stock s = new Stock(str[0].trim(), new BigDecimal(str[1].trim()));
                            stocks.put(s.getTicker(), s);
                        }
                        firstLine = false;
                        line = br.readLine();
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(csvFile + " file not exist");
        }
    }

    @Override
    public void run() {

        while(true){
            for(String ticker : stocks.keySet()){
                Stock s = stocks.get(ticker);

                Random random = new Random();
                Boolean add = random.nextBoolean();
                BigDecimal addition = BigDecimal.valueOf(random.nextDouble());
                BigDecimal new_price = add ? s.getCurr_price().add(addition) : s.getCurr_price().subtract(addition);
                BigDecimal roundValue = new_price.setScale(2, RoundingMode.HALF_UP);

                s.setCurr_price(roundValue);
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

    }

}
