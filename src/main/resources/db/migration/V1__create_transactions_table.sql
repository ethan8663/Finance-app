CREATE TABLE transactions(
     id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
     date DATE NOT NULL,
     amount DECIMAL(12,2) NOT NULL,
     type VARCHAR(10) NOT NULL,
     category_id INT NOT NULL,
     payer VARCHAR(20) NOT NULL,
     payee VARCHAR(20) NOT NULL,
     note VARCHAR(100) NOT NULL,
     recorded_at TIMESTAMP NOT NULL,
     CONSTRAINT fk_transactions_category
         FOREIGN KEY (category_id) REFERENCES categories(id)
)