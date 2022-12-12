SELECT DISTINCT n_name,
CASE
    WHEN l_shipdate LIKE '1994%' THEN
        (SELECT sItems94 - cItems94
        FROM
            (SELECT N1.n_name AS forSupp, N2.n_name AS localCust, count(l_linenumber) AS sItems94 
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1994%'
            GROUP BY forSupp),
            
            (SELECT N1.n_name AS localSupp, N2.n_name AS forCust, count(l_linenumber) AS cItems94
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1994%'
            GROUP BY forCust)
        WHERE forCust = forSupp)

    WHEN l_shipdate LIKE '1995%' THEN
        (SELECT sItems95 - cItems95
        FROM
            (SELECT N1.n_name AS forSupp, N2.n_name AS localCust, count(l_linenumber) AS sItems95
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1995%'
            GROUP BY forSupp),
            
            (SELECT N1.n_name AS localSupp, N2.n_name AS forCust, count(l_linenumber) AS cItems95
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1995%'
            GROUP BY forCust)
        WHERE forCust = forSupp)

    ELSE
        (SELECT sItems96 - cItems96
        FROM
            (SELECT N1.n_name AS forSupp, N2.n_name AS localCust, count(l_linenumber) AS sItems96
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1996%'
            GROUP BY forSupp),
            
            (SELECT N1.n_name AS localSupp, N2.n_name AS forCust, count(l_linenumber) AS cItems96
            FROM nation N1, nation N2 
            JOIN orders ON o_orderkey = l_orderkey
            JOIN lineitem ON l_suppkey = s_suppkey
            JOIN supplier ON s_nationkey = N1.n_nationkey
            JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
            WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1996%'
            GROUP BY forCust)
        WHERE forCust = forSupp)
END
FROM lineitem
JOIN supplier ON s_suppkey = l_suppkey
JOIN nation ON n_nationkey = s_nationkey;