# StockMarketPortfolio
Display Portfolio from mocking data

# Requirement
- OpenJDK 1.8
  https://www.openlogic.com/openjdk-downloads

- H2 Database 1.4.200
  https://h2database.com/h2-2019-10-14.zip

# To build
Clone the repository

`git clone https://github.com/atomicbanana777/StockMarketPortfolio.git`

go to project directory

`cd StockMarketPortfolio`

Run below command to build

`./gradlew build`

`cd app/build/libs`

`app.jar` will be created

# To Run

Copy `portfolio.csv`, `db.csv` and  `mock.csv` from `StockMarketPortfolio/app/src/test/resources`

Paste it at the same directory as `app.jar`

The program will load those files when executing

Run in Linux:

`java -cp app.jar:<h2 database jar> org.example.App`

e.g.

`java -cp app.jar:/home/codespace/h2/bin/h2-1.4.200.jar org.example.App`

Run in window:

`java -cp ".;app.jar;<Path of H2 jar>" org.example.App`

e.g.

`java -cp ".;app.jar;lib\h2-1.4.200.jar" org.example.App`

# Result

```
====================================================================================================================
|Ticker                   |Shares   |Type  |Strike      |Maturity          |Asset    |Price       |Value           |
====================================================================================================================
|MSFT                     |1000     |Stock |N/A         |N/A               |N/A      |495.38      |495380.00       |
|AAPL                     |1000     |Stock |N/A         |N/A               |N/A      |203.61      |203610.00       |
|AAPLPut20250831          |3000     |Put   |205.50      |2025-08-31        |AAPL     |1.89        |5670.00         |
|AAPLCall20250831         |3000     |Call  |195.60      |2025-08-31        |AAPL     |8.01        |24030.00        |
====================================================================================================================


=====================
NAV: 728690.0
=====================
Ctrl + C to exit
```

# To customize profolio

# mock.csv

Mocking data provider of stocks and prices
```
Ticker, Start_Price
AAPL,205.17
MSFT,497.41
GOOG,177.39
AMZN,219.39
META,738.09
TSLA,317.66
TSM,226.49
NVDA,157.99
```

# db.csv

Mocking financial products

```
Ticker, Type, Strike, Maturity, Asset
AAPL,Stock,,,
AAPLCall20250831,Call,195.6,2025-08-31,AAPL
AAPLPut20250831,Put,205.5,2025-08-31,AAPL
MSFT,Stock,,,
GOOG,Stock,,,
AMZN,Stock,,,
META,Stock,,,
TSLA,Stock,,,
TSM,Stock,,,
NVDA,Stock,,,
AMD,Stock,,,
```

# portfolio.csv

The products of portfolio

```
Ticker, Shares
AAPL, 1000
AAPLCall20250831, 3000
AAPLPut20250831, 3000
```
