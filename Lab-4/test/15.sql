SELECT count(DISTINCT o_orderkey)
FROM orders
JOIN customer ON o_custkey = c_custkey
JOIN lineitem ON o_orderkey = l_orderkey
JOIN supplier ON l_suppkey = s_suppkey
WHERE s_acctbal < 0 AND c_acctbal > 0;