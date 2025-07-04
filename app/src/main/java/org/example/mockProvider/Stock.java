package org.example.mockProvider;

import java.math.BigDecimal;

public class Stock {
    private String ticker;
    private BigDecimal curr_price;

    public Stock(String ticker, BigDecimal curr_price){
        this.ticker = ticker;
        this.curr_price = curr_price;
    }

    public String getTicker() {
        return ticker;
    }
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
    public BigDecimal getCurr_price() {
        return curr_price;
    }
    public void setCurr_price(BigDecimal curr_price) {
        this.curr_price = curr_price;
    }
}
