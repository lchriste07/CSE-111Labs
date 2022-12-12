DROP TRIGGER IF EXISTS t5;

CREATE TRIGGER t5 AFTER DELETE ON part 
FOR EACH ROW
BEGIN
    DELETE FROM partsupp
    WHERE ps_partkey = OLD.p_partkey;
    DELETE FROM lineitem
    WHERE l_partkey = OLD.p_partkey;
END;

DELETE FROM part
WHERE p_partkey IN (
    SELECT ps_partkey FROM partsupp
    JOIN supplier ON s_suppkey = ps_suppkey
    JOIN nation ON n_nationkey = s_nationkey
    WHERE n_name IN ('CANADA', 'UNITED STATES'));

SELECT n_name, count(ps_partkey)
FROM partsupp
JOIN supplier ON s_suppkey = ps_suppkey
JOIN nation ON n_nationkey = s_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_name = 'AMERICA'
GROUP BY n_name;