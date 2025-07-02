package org.example.simpleOptionFormula;

import org.example.portfolio.OptionFormula;

public class SimpleOptionFormula implements OptionFormula{

    @Override
    public Double calCallOptionPrice(Double asset_price, Double strike) {
        Double callOption_p = asset_price - strike;
        if(callOption_p < 0) callOption_p = 0.0;
        return callOption_p;
    }

    @Override
    public Double calPutOptionPrice(Double asset_price, Double strike) {
        Double option_p = strike - asset_price;
        if(option_p < 0) option_p = 0.0;
        return option_p;
    }
    
}
