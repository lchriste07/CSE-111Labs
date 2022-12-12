DROP TRIGGER IF EXISTS t1;

CREATE TRIGGER t1 BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
    UPDATE orders
    SET o_orderdate = '2021-12-01'
    WHERE o_orderkey = NEW.o_orderkey;
END;

INSERT INTO orders
SELECT * FROM orders
WHERE o_orderdate LIKE '%1996-12%';

SELECT count(*) FROM orders
WHERE o_orderdate LIKE '%2021%';