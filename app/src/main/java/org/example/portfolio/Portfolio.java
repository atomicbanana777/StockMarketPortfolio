package org.example.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.example.portfolio.factory.DataProviderFactory;
import org.example.portfolio.factory.OptionFormulaFactory;
import org.example.portfolio.factory.PortfolioLoaderFactory;
import org.example.portfolio.interfaceClass.PortfolioLoader;
import org.example.portfolio.interfaceClass.DataProvider;
import org.example.portfolio.interfaceClass.OptionFormula;


public class Portfolio implements Runnable{
    private HashMap<String, Product> products = new HashMap<>();
    private List<PortfolioLoader> loaders = new ArrayList<>();
    private DataProvider dp;
    private OptionFormula of;
    private BigDecimal NAV = new BigDecimal(0.00);
    private final int sleepTime = 1000;

    public Portfolio(){
        this("CSVLoader", "H2Loader", "MockProvider","SimpleOptionFormula");
    }

    public Portfolio(String pLoader1, String pLoader2, String dataProvider, String formula){
        PortfolioLoaderFactory pfactory = new PortfolioLoaderFactory();
        DataProviderFactory dFactory = new DataProviderFactory();
        OptionFormulaFactory oFactory = new OptionFormulaFactory();

        this.setPorfolioLoader(pfactory.getPorfolioLoader(pLoader1))
            .setPorfolioLoader(pfactory.getPorfolioLoader(pLoader2))
            .setDataProvider(dFactory.getDataProvider(dataProvider))
            .setOptionFormula(oFactory.geOptionFormula(formula));
    }

    public HashMap<String, Product> getProducts(){
        return products;
    }

    public void load(){
        for(PortfolioLoader loader : loaders){
            loader.load(products);
        }

        new Thread(this).start();
    }

    public Portfolio setPorfolioLoader(PortfolioLoader loader){
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

            BigDecimal rounded = p.getPrice().multiply(BigDecimal.valueOf(p.getShares())).setScale(2, RoundingMode.HALF_UP);
            p.setValue(rounded);
        }
    }

    public void updateOptionPrice(Product p){
        if(null != p.getType() && p.getType().equals("Call")){
            BigDecimal op = of.calCallOptionPrice(dp.getStockPrice(p.getAsset()), p.getStrike());
            BigDecimal rounded = op.setScale(2, RoundingMode.HALF_UP);
            p.setPrice(rounded);
        } else if (null != p.getType() && p.getType().equals("Put")){
            BigDecimal op = of.calPutOptionPrice(dp.getStockPrice(p.getAsset()), p.getStrike());
            BigDecimal rounded = op.setScale(2, RoundingMode.HALF_UP);
            p.setPrice(rounded);
        }

        if(null != p.getShares() && null != p.getPrice()){
            BigDecimal rounded = p.getPrice().multiply(BigDecimal.valueOf(p.getShares())).setScale(2, RoundingMode.HALF_UP);
            p.setValue(rounded);
        }
       
    }

    public void setNAV(){
        BigDecimal res = new BigDecimal(0.00);
         for(Product p : products.values()){
            if(null != p.getValue()) res = res.add(p.getValue());
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

    public BigDecimal getNAV(){
        return NAV;
    }

}