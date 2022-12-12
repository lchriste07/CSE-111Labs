SELECT n_name
FROM nation
JOIN supplier ON s_nationkey = n_nationkey
JOIN lineitem ON l_suppkey = s_suppkey
JOIN orders ON o_orderkey = l_orderkey
WHERE l_shipdate LIKE '1994%'
GROUP BY n_name
ORDER BY sum(l_extendedprice) DESC
LIMIT 1;