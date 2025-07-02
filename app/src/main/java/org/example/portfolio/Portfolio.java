package org.example.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Portfolio implements Runnable{
    private HashMap<String, Product> products = new HashMap<>();
    private List<PorfolioLoader> loaders = new ArrayList<>();
    private DataProvider dp;
    private OptionFormula of;
    private Double NAV;
    private final int sleepTime = 1000;

    public HashMap<String, Product> getProducts(){
        return products;
    }

    public void load(){
        for(PorfolioLoader loader : loaders){
            loader.load(products);
        }

        new Thread(this).start();
    }

    public Portfolio setPorfolioLoader(PorfolioLoader loader){
        loaders.add(loader);
        return this;
    }

    public Portfolio setDataProvider(DataProvider dp){
        this.dp = dp;
        return this;
    }

    public Portfolio setOptionFormula(OptionFormula of){
        this.of = of;
        return this;
    }

    public void updateStockPrice(Product p){
        if(null != p.getType() && p.getType().equals("Stock")){
            p.setPrice(dp.getStockPrice(p.getTicker()));

            BigDecimal rounded = new BigDecimal( p.getPrice() * p.getShares()).setScale(2, RoundingMode.HALF_UP);
            p.setValue(rounded.doubleValue());
        }
    }

    public void updateOptionPrice(Product p){
        if(null != p.getType() && p.getType().equals("Call")){
            Double op = of.calCallOptionPrice(dp.getStockPrice(p.getAsset()), p.getStrike());
            BigDecimal rounded = new BigDecimal(op).setScale(2, RoundingMode.HALF_UP);
            p.setPrice(rounded.doubleValue());
        } else if (null != p.getType() && p.getType().equals("Put")){
            Double op = of.calPutOptionPrice(dp.getStockPrice(p.getAsset()), p.getStrike());
            BigDecimal rounded = new BigDecimal(op).setScale(2, RoundingMode.HALF_UP);
            p.setPrice(rounded.doubleValue());
        }

        if(null != p.getShares() && null != p.getPrice()){
            BigDecimal rounded = new BigDecimal( p.getPrice() * p.getShares()).setScale(2, RoundingMode.HALF_UP);
            p.setValue(rounded.doubleValue());
        }
       
    }

    public void setNAV(){
        Double res = 0.0;
         for(Product p : products.values()){
            if(null != p.getValue()) res += p.getValue();
        }
        this.NAV = res;
    }

    @Override
    public void run() {
        try {
            while(true){
                for(Product p : products.values()){
                    updateStockPrice(p);
                    updateOptionPrice(p);
                }
                setNAV();
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Double getNAV(){
        return NAV;
    }

}