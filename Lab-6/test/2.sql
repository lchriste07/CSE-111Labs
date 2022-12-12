SELECT count(c_custkey)
FROM customer
WHERE c_custkey IN 
(SELECT o_custkey FROM customer
JOIN orders ON o_custkey = c_custkey
WHERE o_orderdate LIKE '1995-11%'
GROUP BY c_custkey
HAVING count(c_custkey) >= 3);