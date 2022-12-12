SELECT o_orderpriority, count(p_name)
FROM part
JOIN lineitem ON l_partkey = p_partkey
JOIN orders ON o_orderkey = l_orderkey
WHERE o_orderdate LIKE '1997-_%' AND l_receiptdate > l_commitdate
GROUP BY o_orderpriority;