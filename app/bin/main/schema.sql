CREATE TABLE IF NOT EXISTS security (
    id INT PRIMARY KEY AUTO_INCREMENT, 
    ticker VARCHAR(255), 
    type VARCHAR(255), 
    strike NUMERIC(20,2), 
    maturity DATE, 
    asset VARCHAR(255)
);