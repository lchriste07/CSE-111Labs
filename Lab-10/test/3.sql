DROP TRIGGER IF EXISTS t3;

CREATE TRIGGER t3 BEFORE UPDATE ON customer
FOR EACH ROW
WHEN (NEW.c_acctbal > 0)
BEGIN
    UPDATE customer
    SET c_comment = 'Positive balance'
    WHERE c_acctbal = NEW.c_acctbal;
END;

UPDATE customer
SET c_acctbal = 100
WHERE c_nationkey IN (
    SELECT n_nationkey
    FROM nation
    WHERE n_name = 'UNITED STATES');

SELECT count(*) 
FROM customer
JOIN nation ON c_nationkey = n_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_name = 'AMERICA' AND c_acctbal < 0;