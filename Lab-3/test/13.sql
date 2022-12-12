SELECT sum(c_acctbal)
FROM customer
JOIN nation ON n_nationkey = c_nationkey
JOIN region ON r_regionkey = n_regionkey
WHERE r_regionkey = 2 AND c_mktsegment = 'MACHINERY';