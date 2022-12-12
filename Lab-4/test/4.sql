SELECT s_name, count(p_partkey)
FROM part, nation
JOIN partsupp ON ps_suppkey = s_suppkey AND ps_partkey = p_partkey
JOIN supplier ON s_nationkey = n_nationkey
WHERE p_size < 20 AND s_nationkey = 3
GROUP BY s_name;