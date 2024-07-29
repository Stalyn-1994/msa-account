-- Create schema if not exist
CREATE SCHEMA IF NOT EXISTS account;

-- Create table account if not exist
CREATE TABLE IF NOT EXISTS account.account
(
    id              SERIAL PRIMARY KEY,
    customer        VARCHAR(255)             NOT NULL,
    customer_id        VARCHAR(255)             NOT NULL,
    account_number  VARCHAR(255)             NOT NULL,
    type            VARCHAR(255)             NOT NULL,
    initial_balance DOUBLE PRECISION,
    status          BOOLEAN                           DEFAULT FALSE,
    created_on      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      TIMESTAMP WITH TIME ZONE
);

-- Create table movements if not exist
CREATE TABLE IF NOT EXISTS account.movements
(
    id             SERIAL PRIMARY KEY,
    date           timestamp                NOT NULL,
    type           VARCHAR(255)             NOT NULL,
    amount         DOUBLE PRECISION,
    balance        DOUBLE PRECISION,
    account_number BIGINT
        CONSTRAINT account_number_id_fk references account.account,
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_account_id ON account.account(account_number);