SELECT p_name
FROM part
JOIN partsupp ON ps_partkey = p_partkey
JOIN supplier ON s_suppkey = ps_suppkey
JOIN nation ON n_nationkey = s_nationkey
WHERE n_name = 'UNITED STATES'
ORDER BY ps_supplycost * ps_availqty DESC
LIMIT 5;