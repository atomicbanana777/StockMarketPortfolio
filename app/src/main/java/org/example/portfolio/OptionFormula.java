package org.example.portfolio;

public interface OptionFormula {

    public Double calCallOptionPrice(Double asset_price, Double strike);
    public Double calPutOptionPrice(Double asset_price, Double strike);
    
}
