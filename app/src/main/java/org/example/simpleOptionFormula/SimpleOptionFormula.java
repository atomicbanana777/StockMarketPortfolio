package org.example.simpleOptionFormula;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.example.portfolio.interfaceClass.OptionFormula;

public class SimpleOptionFormula implements OptionFormula{

    @Override
    public BigDecimal calCallOptionPrice(BigDecimal asset_price, BigDecimal strike) {
        BigDecimal option_p = asset_price.subtract(strike);
        if(option_p.compareTo(new BigDecimal(0)) < 0) option_p = new BigDecimal(0);
        return option_p.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calPutOptionPrice(BigDecimal asset_price, BigDecimal strike) {
        BigDecimal option_p = strike.subtract(asset_price);
        if(option_p.compareTo(new BigDecimal(0)) < 0) option_p = new BigDecimal(0);
        return option_p.setScale(2, RoundingMode.HALF_UP);
    }
    
}
