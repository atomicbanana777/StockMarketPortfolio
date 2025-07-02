package org.example.csvLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.example.portfolio.PorfolioLoader;
import org.example.portfolio.Product;

public class CSVLoader implements PorfolioLoader{

    @Override
    public void load(HashMap<String, Product> products){
        File f = new File("portfolio.csv");
        InputStream is = getClass().getClassLoader().getResourceAsStream("portfolio.csv");
        if(f.exists() || is != null){
            if(f.exists()){
                try {
                    is = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            try (BufferedReader bf = new BufferedReader(new InputStreamReader(is));) {
                String line = bf.readLine();
                boolean firstLine = true;

                while (line != null) {
                    if(!firstLine){ //skip 1st line
                        Product p = setProductFromCSVRow(line);
                        products.put(p.getTicker(), p);
                    }
                    firstLine = false;
                    line = bf.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("CSV file not exist");
        }
    }

    public Product setProductFromCSVRow(String line){
        Product p = new Product();
        String[] str_array = line.split(",");
        for(int i = 0 ; i < str_array.length ; i++){
            String s = str_array[i].trim();
            switch (i) {
                case 0:
                    p.setTicker(s);
                    break;
                case 1:
                    p.setShares(Integer.parseInt(s));
                    break;
                default:
                    break;
            }
        }
        return p;
    }
    
}
