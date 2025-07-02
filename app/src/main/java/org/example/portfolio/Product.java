package org.example.portfolio;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
    private String ticker;
    private Integer shares;
    private String type;
    private Double strike;
    private Date maturity;
    private Double price;
    private String asset;
    private Double value;

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public Integer getShares() {
        return shares;
    }
    public void setShares(Integer shares) {
        this.shares = shares;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getStrike() {
        return strike;
    }
    public void setStrike(Double strike) {
        this.strike = strike;
    }
    public Date getMaturity() {
        return maturity;
    }
    public void setMaturity(Date maturity) {
        this.maturity = maturity;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getAsset() {
        return asset;
    }
    public void setAsset(String asset) {
        this.asset = asset;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String res = "|" + String.format("%-25s", ticker) + "|" + String.format("%-9d", shares) + "|";
        if(null != type){
            if(type.equals("Stock")){
                res = res + String.format("%-6s", type) + "|" +
                    String.format("%-12s", "N/A") + "|" +
                    String.format("%-18s", "N/A" ) + "|" +
                    String.format("%-9s", "N/A") + "|" +
                    String.format("%-12.2f", price) + "|" +
                    String.format("%-16.2f", value) + "|";
            } else {
                res = res + String.format("%-6s", type) + "|" +
                    String.format("%-12.2f", strike) + "|" +
                    String.format("%-18s", dateFormat.format(maturity) ) + "|" +
                    String.format("%-9s", asset) + "|" +
                    String.format("%-12.2f", price) + "|" +
                    String.format("%-16.2f", value) + "|";
            }
        }
        return res; 
    }
}