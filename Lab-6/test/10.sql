SELECT r_name
FROM region
JOIN customer ON c_nationkey = n_nationkey
JOIN supplier ON s_nationkey = n_nationkey
JOIN nation ON n_regionkey = r_regionkey
JOIN orders ON o_custkey = c_custkey
JOIN lineitem ON l_orderkey = o_orderkey
GROUP BY r_name
ORDER BY sum(l_extendedprice)
LIMIT 1;