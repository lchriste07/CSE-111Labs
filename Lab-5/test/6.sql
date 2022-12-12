SELECT p_mfgr
FROM part
JOIN partsupp ON ps_partkey = p_partkey
JOIN supplier ON s_suppkey = ps_suppkey
WHERE s_suppkey = 10
ORDER BY ps_availqty
LIMIT 1;