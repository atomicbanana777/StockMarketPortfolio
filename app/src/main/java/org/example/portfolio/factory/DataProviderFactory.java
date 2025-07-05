package org.example.portfolio.factory;

import org.example.mockProvider.MockProvider;
import org.example.portfolio.interfaceClass.DataProvider;

public class DataProviderFactory {
    public DataProvider getDataProvider(String name){
        DataProvider res = null;
        MockProvider mp;
        switch (name) {
            case "MockProvider":
                mp = new MockProvider();
                mp.start();
                res = mp;
                break;
            case "MockProvider_R":
                mp = new MockProvider();
                mp.setResourceURL(getClass().getClassLoader().getResource(mp.getCSVFile()));
                mp.start();
                res = mp;
                break;
            default:
                throw new IllegalArgumentException("no such data provider");
        }
        return res;
    }
}
