MERGE INTO account (id, account_number, holder_name, balance) KEY(id) VALUES (1, '001-000001', 'Ana Perez', 150000.00);
MERGE INTO account (id, account_number, holder_name, balance) KEY(id) VALUES (2, '001-000002', 'Luis Soto', 245000.50);
ALTER TABLE account ALTER COLUMN id RESTART WITH 3;
