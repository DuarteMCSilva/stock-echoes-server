insert into portfolios_table (id, name) values
(10001, 'my-portfolio'),
(10002, 'empty-portfolio');

INSERT INTO ticker_table (isin, symbol, company_name ) VALUES
( 'US1', 'AAPL', 'Apple Inc.'),
( 'US2', 'GOOGL', 'Alphabet Inc.'),
( 'US3', 'AMZN', 'Amazon.com Inc.');


INSERT INTO price_entry (id, ticker_id, price, date) VALUES
(10001, 'US1', 145.23, '2025-02-01'),
(10002, 'US1', 146.50, '2025-02-02'),
(10003, 'US1', 147.12, '2025-02-03'),
(10004, 'US2', 2752.40, '2025-02-01'),
(10005, 'US2', 2755.15, '2025-02-02'),
(10006, 'US2', 2760.20, '2025-02-03'),
(10007, 'US3', 3450.01, '2025-02-01'),
(10008, 'US3', 3445.88, '2025-02-02'),
(10009, 'US3', 3460.00, '2025-02-03'),
(10016, 'US1', 158.78, '2025-02-04');



INSERT INTO transaction_table (id, cost, date, quantity, portfolio_id, isin) VALUES
(10001, 150.75, '2024-02-15',  10, 10001, 'US1'),
(10002, 200.50, '2024-02-16',  15, 10001, 'US2'),
(10003, 180.20, '2024-02-20', -12, 10001, 'US2'),
(10004, 95.60,  '2024-02-21',   6, 10001, 'US1'),
(10005, 250.80, '2024-02-22',  18, 10001, 'US2'),
(10006, 175.99, '2024-02-23',  11, 10001, 'US1'),
(10007, 130.45, '2024-02-24',  -9, 10001, 'US2');

INSERT INTO se_users (id, username, password, role) VALUES
(10001, 'admin', '$2a$12$6pgXp3jaRMKQlliA8pDdXOWvOaW5b86ct.icjD4TygnUO3rSCgqBu', 'user,admin'),
(10002, 'user', '$2a$12$TdkZPIy5xkCSM/PNwzb8wu95VapYIwoQ6QpOr9c5GU4/vgsKyeX2.', 'user');

