package org.example.portfolio.factory;

import org.example.csvLoader.CSVLoader;
import org.example.h2Loader.H2Loader;
import org.example.portfolio.interfaceClass.PortfolioLoader;

public class PortfolioLoaderFactory {

    public PortfolioLoader getPorfolioLoader(String name){
        PortfolioLoader res = null;
        switch (name) {
            case "CSVLoader":
                res = new CSVLoader();
                break;
            case "CSVLoader_R":
                CSVLoader cl = new CSVLoader();
                cl.setResourceURL(getClass().getClassLoader().getResource(cl.getCSVFile()));
                res = cl;
                break;
            case "H2Loader":
                res = new H2Loader();
                break;
            case "H2Loader_R":
                H2Loader hl = new H2Loader();
                hl.setResourceURL(getClass().getClassLoader().getResource(hl.getCSVFile()));
                res = hl;
                break;
            default:
                throw new IllegalArgumentException("no such portfolio loader");
        }
        return res;
    }
    
}
