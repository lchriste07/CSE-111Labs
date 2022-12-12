SELECT count(DISTINCT c_custkey)
FROM customer
JOIN orders ON o_custkey = c_custkey
WHERE NOT o_orderkey IN 
    (SELECT o_orderkey FROM nation
    JOIN customer ON c_custkey = o_custkey
    JOIN orders ON o_orderkey = l_orderkey
    JOIN lineitem ON l_suppkey = s_suppkey
    JOIN supplier ON s_nationkey = n_nationkey
    JOIN region ON r_regionkey = n_regionkey
    WHERE r_name <> 'AMERICA');