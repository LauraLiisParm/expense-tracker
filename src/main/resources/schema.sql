-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-12 07:02:56.219

-- tables
-- Table: budgets
CREATE TABLE budgets
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1) PRIMARY KEY,
    budget_month  DATE         NOT NULL,
    budget_year   SMALLINT     NOT NULL,
    budget_amount DECIMAL      NOT NULL,
    name          varchar(255) not null,
    description   varchar(255) not null,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- End of file.

