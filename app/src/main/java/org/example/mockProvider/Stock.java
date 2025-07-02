package org.example.mockProvider;

public class Stock {
    private String ticker;
    private Double curr_price;

    public Stock(String ticker, Double curr_price){
        this.ticker = ticker;
        this.curr_price = curr_price;
    }

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public Double getCurr_price() {
        return curr_price;
    }
    public void setCurr_price(Double curr_price) {
        this.curr_price = curr_price;
    }
}
