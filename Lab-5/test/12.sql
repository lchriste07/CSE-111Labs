SELECT sum(ps_supplycost)
FROM partsupp S1
JOIN part ON p_partkey = ps_partkey
JOIN lineitem ON l_partkey = ps_partkey
WHERE p_retailprice < 1000 AND l_shipdate LIKE '1997%' AND NOT S1.ps_suppkey IN
    (SELECT ps_suppkey FROM partsupp S2
    JOIN lineitem ON l_suppkey = S1.ps_suppkey
    WHERE S2.ps_suppkey = S1.ps_suppkey AND l_extendedprice < 2000 AND l_shipdate LIKE '1996%');