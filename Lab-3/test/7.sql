SELECT STRFTIME('%Y-%m', l_receiptdate), count(l_receiptdate)
FROM orders, lineitem
WHERE o_custkey = 20 AND l_orderkey = o_orderkey
GROUP BY STRFTIME('%Y-%m', l_receiptdate);