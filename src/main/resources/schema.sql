-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-12 07:02:56.219

-- tables
-- Table: budget
CREATE TABLE budget (
    budget_id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    category_id int  NOT NULL,
    budget_month date  NOT NULL,
    budget_year smallint  NOT NULL,
    budget_amount decimal(10,2)  NOT NULL,
    expense_amount decimal(10,2)  NULL DEFAULT 0,
    created_at timestamp  NULL DEFAULT current_timestamp,
    CONSTRAINT budget_pk PRIMARY KEY (budget_id)
);

-- Table: category
CREATE TABLE category (
    category_id int GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
    name varchar(100)  NOT NULL,
    description varchar(255)  NULL,
    CONSTRAINT AK_0 UNIQUE (name),
    CONSTRAINT category_pk PRIMARY KEY (category_id)
);

-- foreign keys
-- Reference: FK_0 (table: budget)
ALTER TABLE budget ADD CONSTRAINT FK_0
    FOREIGN KEY (category_id)
    REFERENCES category (category_id);

-- End of file.

