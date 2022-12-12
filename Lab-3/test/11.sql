SELECT count(DISTINCT o_custkey)
FROM orders, lineitem 
WHERE l_discount >= 0.07 AND l_orderkey = o_orderkey;