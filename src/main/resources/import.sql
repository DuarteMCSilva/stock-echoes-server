INSERT INTO se_tickers (isin, symbol, company_name, security_type ) VALUES
( 'US1', 'AAPL', 'Apple Inc.', 'Common Stock'),
( 'US2', 'GOOGL', 'Alphabet Inc.', 'Common Stock'),
( 'US3', 'AMZN', 'Amazon.com Inc.', 'Common Stock');

INSERT INTO se_customers (id, firstName, lastName ) values
(10001, 'Ekki', 'Bagato'),
(10002, 'Joana', 'Patinha');

INSERT INTO se_accounts (id, customer_id, email ) values
(10001, 10001, 'ekki@cat.se'),
(10002, 10002, 'ju@pata.se');

INSERT INTO se_credentials (id, account_id, username, password, email, role) VALUES
(10001, 10001, 'ekkib','$2a$12$6pgXp3jaRMKQlliA8pDdXOWvOaW5b86ct.icjD4TygnUO3rSCgqBu','ekki-info@cat.se','user,admin'),
(10002, 10002, 'joanap','$2a$12$TdkZPIy5xkCSM/PNwzb8wu95VapYIwoQ6QpOr9c5GU4/vgsKyeX2.','ju-info@pata.se', 'user');

insert into se_portfolios (id, name, account_id) values
(10001, 'Royal Catin', 10001),
(10002, 'PURRina One', 10001),
(10003, 'Space Weather', 10002),
(10004, 'Horses', 10002);

INSERT INTO se_transactions (id, cost, date, quantity, portfolio_id, isin) VALUES
(10001, 150.75, '2024-02-15',  10, 10001, 'US1'),
(10002, 200.50, '2024-02-16',  15, 10001, 'US2'),
(10003, 180.20, '2024-02-20', -12, 10001, 'US2'),
(10004, 95.60,  '2024-02-21',   6, 10001, 'US1'),
(10005, 250.80, '2024-02-22',  18, 10001, 'US2'),
(10006, 175.99, '2024-02-23',  11, 10001, 'US1'),
(10007, 130.45, '2024-02-24',  -9, 10001, 'US2');
