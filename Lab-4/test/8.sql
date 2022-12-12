SELECT n_name, count(DISTINCT o_orderkey)
FROM orders
JOIN lineitem ON l_orderkey = o_orderkey
JOIN supplier ON s_suppkey = l_suppkey
JOIN nation ON n_nationkey = s_nationkey
WHERE o_orderdate LIKE '1995-___%' AND o_orderstatus = 'F'
GROUP BY n_name
HAVING count(DISTINCT o_orderkey) > 50;