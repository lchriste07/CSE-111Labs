SELECT n_name, count(o_orderkey)
FROM orders
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_regionkey = 1
GROUP BY n_name;