SELECT sum(l_extendedprice*(1-l_discount) / (
    SELECT sum(l_extendedprice*(1-l_discount)) 
    FROM nation N1, nation N2
    JOIN customer ON c_nationkey = N1.n_nationkey
    JOIN region ON r_regionkey = N1.n_regionkey
    JOIN supplier ON s_nationkey = N2.n_nationkey
    JOIN lineitem ON l_suppkey = s_suppkey
    JOIN orders ON o_orderkey = l_orderkey
    WHERE l_shipdate LIKE '1997%' AND NOT N2.n_nationkey IN (SELECT n_nationkey FROM nation WHERE n_nationkey = 24))
FROM nation N1, nation N2
JOIN customer ON c_nationkey = N1.n_nationkey
JOIN region ON r_regionkey = N1.n_regionkey
JOIN supplier ON s_nationkey = N2.n_nationkey
JOIN lineitem ON l_suppkey = s_suppkey
JOIN orders ON o_orderkey = l_orderkey
WHERE l_shipdate LIKE '1997%' AND N2.n_nationkey = 24;