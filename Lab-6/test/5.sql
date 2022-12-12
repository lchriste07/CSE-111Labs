SELECT count(ps_suppkey)
FROM partsupp
JOIN part ON p_partkey = ps_partkey
WHERE p_retailprice = (SELECT max(p_retailprice) FROM part);