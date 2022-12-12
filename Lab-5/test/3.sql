SELECT l_discount
FROM lineitem, (
    SELECT avg(l_discount) AS avg_discount
    FROM lineitem
    )
JOIN orders ON o_orderkey = l_orderkey
WHERE o_orderdate LIKE '1996-10-__%' AND l_discount > avg_discount
LIMIT 1;