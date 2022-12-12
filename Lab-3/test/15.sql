SELECT STRFTIME('%Y', o_orderdate), count(l_orderkey)
FROM lineitem
JOIN orders ON o_orderkey = l_orderkey
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = s_nationkey
JOIN supplier ON s_suppkey = l_suppkey
WHERE o_orderpriority = '3-MEDIUM' AND (s_nationkey = 7 OR s_nationkey = 6)
GROUP BY STRFTIME('%Y', o_orderdate);