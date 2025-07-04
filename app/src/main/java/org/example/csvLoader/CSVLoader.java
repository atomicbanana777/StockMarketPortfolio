package org.example.csvLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.example.portfolio.interfaceClass.PortfolioLoader;
import org.example.portfolio.Product;

public class CSVLoader implements PortfolioLoader{

    private static final String csvFile = "portfolio.csv";
    private URL resourceURL = null;

    public String getCSVFile(){
        return csvFile;
    }

    public void setResourceURL(URL resourceURL){
        this.resourceURL = resourceURL;
    }

    @Override
    public void load(HashMap<String, Product> products){
        File f = new File(csvFile);
        if(f.exists()){
            try {
                setResourceURL(f.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        
        if(resourceURL != null){
            try (InputStream is = resourceURL.openStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));) {
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
            System.out.println(csvFile + " file not exist");
        }
    }

    public static Product setProductFromCSVRow(String line){
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
