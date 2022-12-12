SELECT n_name, count(DISTINCT c_custkey), count(DISTINCT s_suppkey)
FROM customer, supplier
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_regionkey = 0 AND c_nationkey = s_nationkey
GROUP BY n_name;