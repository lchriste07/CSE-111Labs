SELECT count(c_custkey)
FROM customer
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE n_regionkey <> 0 AND n_regionkey <> 2 AND n_regionkey <> 3;