DROP TRIGGER IF EXISTS t4_insert;
DROP TRIGGER IF EXISTS t4_delete;

CREATE TRIGGER t4_insert AFTER INSERT ON lineitem
FOR EACH ROW
BEGIN
    UPDATE ORDERS
    SET o_orderpriority = 'HIGH'
    WHERE o_orderkey = NEW.l_orderkey;
END;

CREATE TRIGGER t4_delete AFTER DELETE ON lineitem
FOR EACH ROW
BEGIN
    UPDATE ORDERS
    SET o_orderpriority = 'HIGH'
    WHERE o_orderkey = OLD.l_orderkey;
END;

DELETE FROM lineitem
WHERE l_orderkey IN (
    SELECT l_orderkey FROM lineitem
    JOIN orders ON o_orderkey = l_orderkey
    WHERE o_orderdate LIKE '%1995-12%');

SELECT count(*)
FROM orders
WHERE o_orderpriority LIKE '%HIGH%' AND 
    o_orderdate LIKE '%1995-12%';
 