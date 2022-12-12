SELECT r_name, count(DISTINCT c_custkey)
FROM customer, orders, (SELECT avg(c_acctbal) AS avg_bal FROM customer)
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE c_acctbal < avg_bal AND NOT c_custkey IN (SELECT o_custkey FROM orders)
GROUP BY r_name;