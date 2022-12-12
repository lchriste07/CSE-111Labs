SELECT count(l_orderkey)
FROM lineitem
JOIN supplier ON s_suppkey = l_suppkey
JOIN nation ON n_nationkey = s_nationkey
JOIN orders ON o_orderkey = l_orderkey
JOIN customer ON c_custkey = o_custkey
WHERE c_nationkey = 24 AND n_regionkey = 0;