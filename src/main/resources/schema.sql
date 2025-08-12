-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-12 07:02:56.219

-- tables
-- Table: budget
CREATE TABLE budget (
                        budget_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1) PRIMARY KEY,
                        category_id INT NOT NULL,
                        budget_month DATE NOT NULL,
                        budget_year SMALLINT NOT NULL,
                        budget_amount DECIMAL(10,2) NOT NULL,
                        expense_amount DECIMAL(10,2) DEFAULT 0,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Table: category
CREATE TABLE category (
                          category_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1) PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description VARCHAR(255),
                          CONSTRAINT AK_0 UNIQUE (name)
);

-- foreign keys
-- Reference: FK_0 (table: budget)
ALTER TABLE budget ADD CONSTRAINT FK_0
    FOREIGN KEY (category_id)
    REFERENCES category (category_id);

-- End of file.

