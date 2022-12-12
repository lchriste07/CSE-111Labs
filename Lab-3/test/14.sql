SELECT count(o_orderpriority)
FROM orders
JOIN customer ON c_custkey = o_custkey
JOIN nation ON n_nationkey = c_nationkey
WHERE c_nationkey = 17 AND o_orderpriority = '1-URGENT' AND
    (o_orderdate LIKE '1995-___%'OR o_orderdate LIKE '1996-___%' OR o_orderdate LIKE '1997-___%');