SELECT count(DISTINCT o_clerk)
FROM orders
JOIN lineitem ON l_orderkey = o_orderkey
JOIN supplier ON s_suppkey = l_suppkey
JOIN nation ON n_nationkey = s_nationkey
WHERE s_nationkey = 24;