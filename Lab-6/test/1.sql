SELECT strftime('%m', l_shipdate), sum(l_quantity)
FROM lineitem
WHERE l_shipdate LIKE '1995%'
GROUP BY strftime('%m', l_shipdate);
