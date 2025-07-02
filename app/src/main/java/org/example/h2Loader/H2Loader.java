package org.example.h2Loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.example.portfolio.PorfolioLoader;
import org.example.portfolio.Product;

public class H2Loader implements PorfolioLoader{
    private static final String JDBC_URL = "jdbc:h2:mem:demodb;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private Connection connection;

    public H2Loader(){
        initDatabase();
    }

    public void initDatabase() {
        try {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            
            File f = new File("db.csv");
            InputStream is = getClass().getClassLoader().getResourceAsStream("db.csv");
            
            if(f.exists() || is != null){
                if(f.exists()){
                    try {
                        is = new FileInputStream(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(is));) {
                    boolean firstLine = true;
                    String line = bf.readLine();
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO security (ticker, type, strike, maturity, asset) VALUES (?, ?, ?, ?, ?)");

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
                    ps.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("CSV file not exist");
                return;
            }

          } catch (SQLException e) {
              e.printStackTrace();
          }
    }

    @Override
    public void load(HashMap<String, Product> products) {
        for(Product p : products.values()){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT type, strike, maturity, asset FROM security where ticker = ?");
                ps.setString(1, p.getTicker());
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    p.setType(resultSet.getString("type"));
                    p.setStrike(resultSet.getDouble("strike"));
                    p.setMaturity(resultSet.getDate("maturity"));
                    p.setAsset(resultSet.getString("asset"));
                }
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    
}
