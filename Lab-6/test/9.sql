SELECT DISTINCT p_name
FROM part
JOIN lineitem ON l_partkey = p_partkey
JOIN orders ON o_orderkey = l_orderkey
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_name = 'AMERICA' AND p_name IN 
    (SELECT p_name FROM part
    JOIN lineitem ON l_partkey = p_partkey
    JOIN supplier ON s_suppkey = l_suppkey
    JOIN nation ON n_nationkey = s_nationkey
    JOIN region ON r_regionkey = n_regionkey
    WHERE r_name = 'ASIA'
    GROUP BY p_name
    HAVING count(s_suppkey) = 3);