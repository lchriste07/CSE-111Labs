DROP TRIGGER IF EXISTS t2;

CREATE TRIGGER t2 BEFORE UPDATE ON customer
FOR EACH ROW
WHEN (NEW.c_acctbal < 0)
BEGIN
    UPDATE customer
    SET c_comment = 'Negative balance!!!'
    WHERE c_acctbal = NEW.c_acctbal;
END;

UPDATE customer
SET c_acctbal = -100
WHERE c_nationkey IN (
    SELECT n_nationkey
    FROM nation
    JOIN region ON r_regionkey = n_regionkey
    WHERE r_name = 'AMERICA');

SELECT count(*) 
FROM customer
JOIN nation ON c_nationkey = n_nationkey
WHERE n_name = 'CANADA' AND c_acctbal < 0;