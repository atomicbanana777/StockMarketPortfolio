package org.example.mockProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Random;

import org.example.portfolio.DataProvider;

public class MockProvider implements DataProvider, Runnable{
    HashMap<String, Stock> stocks = new HashMap<>();
    private final int sleepTime = 1000;

    public MockProvider(){
        load();
        new Thread(this).start();
    }

    @Override
    public Double getStockPrice(String ticker) {
        return stocks.get(ticker).getCurr_price();
    }

    public void load(){
        File file = new File("mock.csv");
        InputStream is = getClass().getClassLoader().getResourceAsStream("mock.csv");
        if(file.exists() || is != null){
            if(file.exists()){
                try {
                    is = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is));){
                    boolean firstLine = true;
                    String line = br.readLine();
                    while (line != null) {
                        if(!firstLine){ //skip 1st line
                            String[] str = line.split(",");
                            Stock s = new Stock(str[0].trim(), Double.parseDouble(str[1].trim()));
                            stocks.put(s.getTicker(), s);
                        }
                        firstLine = false;
                        line = br.readLine();
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("mock.csv not exist");
        }
    }

    @Override
    public void run() {

        while(true){
            for(String ticker : stocks.keySet()){
                Stock s = stocks.get(ticker);

                Random random = new Random();
                Boolean add = random.nextBoolean();
                Double addition = random.nextDouble();
                Double new_price = add ? s.getCurr_price() + addition : s.getCurr_price() - addition;
                BigDecimal bd = new BigDecimal(new_price).setScale(2, RoundingMode.HALF_UP);
                double roundedValue = bd.doubleValue();

                s.setCurr_price(roundedValue);
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

    }

}
