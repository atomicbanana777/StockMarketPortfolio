package org.example.h2Loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.example.portfolio.interfaceClass.PortfolioLoader;
import org.example.portfolio.Product;

public class H2Loader implements PortfolioLoader{
    private static final String JDBC_URL = "jdbc:h2:mem:demodb;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String csvFile = "db.csv";
    private URL resourceURL = null;
    private Connection connection;

    public String getCSVFile(){
        return csvFile;
    }

    public void setResourceURL(URL resourceURL){
        this.resourceURL = resourceURL;
    }

    public void initDatabase() {
        File f = new File(csvFile);
        if(f.exists()){
            try {
                setResourceURL(f.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if(resourceURL != null){
            try {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (InputStream is = resourceURL.openStream();
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                PreparedStatement ps = connection.prepareStatement("INSERT INTO security (ticker, type, strike, maturity, asset) VALUES (?, ?, ?, ?, ?)");) {

                boolean firstLine = true;
                String line = bf.readLine();
                while (line != null) {
                    if(!firstLine){ //skip 1st line
                        String[] str = line.split(",");
                        for(int i = 0; i < str.length ; i++){
                            ps.setString(i + 1, "" != str[i].trim() ? str[i].trim() : null);    
                        }
                        for(int j = 5 ; j > str.length ; j--){
                            ps.setString(j, null);
                        }
                        ps.executeUpdate();
                    }
                    firstLine = false;
                    line = bf.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(csvFile +" file not exist");
            return;
        }
    }

    @Override
    public void load(HashMap<String, Product> products) {
        initDatabase();
        for(Product p : products.values()){
            try (PreparedStatement ps = connection.prepareStatement("SELECT type, strike, maturity, asset FROM security where ticker = ?");){
                
                ps.setString(1, p.getTicker());
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    p.setType(resultSet.getString("type"));
                    p.setStrike(resultSet.getBigDecimal("strike"));
                    p.setMaturity(resultSet.getDate("maturity"));
                    p.setAsset(resultSet.getString("asset"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    
}
