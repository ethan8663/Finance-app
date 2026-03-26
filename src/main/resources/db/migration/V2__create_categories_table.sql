CREATE TABLE categories
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50)             NOT NULL,
    type ENUM ('INCOME','EXPENSE') NOT NULL,
    UNIQUE KEY uq_category_name_type (name, type)
);