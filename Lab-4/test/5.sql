SELECT c_name, count(o_custkey)
FROM orders
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = c_nationkey
WHERE c_nationkey = 7 AND o_orderdate LIKE '1993-___%'
GROUP BY c_name;