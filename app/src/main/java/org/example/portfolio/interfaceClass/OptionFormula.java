package org.example.portfolio.interfaceClass;

import java.math.BigDecimal;

public interface OptionFormula {

    public BigDecimal calCallOptionPrice(BigDecimal asset_price, BigDecimal strike);
    public BigDecimal calPutOptionPrice(BigDecimal asset_price, BigDecimal strike);
    
}
