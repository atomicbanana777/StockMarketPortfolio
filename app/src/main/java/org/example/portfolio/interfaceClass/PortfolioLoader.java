package org.example.portfolio.interfaceClass;

import java.util.HashMap;

import org.example.portfolio.Product;

public interface PortfolioLoader {
    public void load(HashMap<String, Product> products);
}
