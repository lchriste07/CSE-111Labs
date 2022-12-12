SELECT p_name
FROM part
JOIN lineitem ON l_partkey = p_partkey
WHERE l_shipdate > '1996-10-02'
ORDER BY l_extendedprice*(1-l_discount)
LIMIT 1;