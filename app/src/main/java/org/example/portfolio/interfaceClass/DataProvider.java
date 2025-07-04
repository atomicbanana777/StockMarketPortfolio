package org.example.portfolio.interfaceClass;

import java.math.BigDecimal;

public interface DataProvider {
    public BigDecimal getStockPrice(String ticker);
}
