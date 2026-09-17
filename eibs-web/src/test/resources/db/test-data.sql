INSERT INTO account (id, account_number, holder_name, balance) VALUES (1, 'LAB-1001', 'Maria Lopez', 100.00);
INSERT INTO account (id, account_number, holder_name, balance) VALUES (2, 'LAB-1002', 'Pedro Rojas', 200.00);
ALTER TABLE account ALTER COLUMN id RESTART WITH 3;
