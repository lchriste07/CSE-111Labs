SELECT n_name, o_orderstatus, count(o_custkey)
FROM orders
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE n_regionkey = 1
GROUP BY n_name, o_orderstatus;