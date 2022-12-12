SELECT R1.r_name, R2.r_name, strftime('%Y', l_shipdate), sum(l_extendedprice*(1-l_discount))
FROM nation N1, nation N2
JOIN supplier ON s_nationkey = N1.n_nationkey
JOIN customer ON c_nationkey = N2.n_nationkey
JOIN lineitem ON l_suppkey = s_suppkey
JOIN orders ON o_custkey = c_custkey AND o_orderkey = l_orderkey
JOIN region R1 ON R1.r_regionkey = N1.n_regionkey
JOIN region R2 ON R2.r_regionkey = N2.n_regionkey
WHERE l_shipdate LIKE '1996%' OR l_shipdate LIKE '1997%'
GROUP BY R1.r_name, R2.r_name, strftime('%Y', l_shipdate);