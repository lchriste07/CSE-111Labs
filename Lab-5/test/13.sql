SELECT o_orderpriority, count(DISTINCT o_orderkey)
FROM orders
JOIN lineitem ON l_orderkey = o_orderkey
WHERE o_orderdate >= '1997-10-01' AND o_orderdate <= '1997-12-31' AND l_receiptdate < l_commitdate
GROUP BY o_orderpriority;
