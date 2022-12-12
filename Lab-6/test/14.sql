SELECT forCust, numSuppItems - numCustItems
FROM 
    (SELECT N1.n_name AS forSupp, N2.n_name AS localCust, count(l_linenumber) AS numSuppItems 
    FROM nation N1, nation N2 
    JOIN orders ON o_orderkey = l_orderkey
    JOIN lineitem ON l_suppkey = s_suppkey
    JOIN supplier ON s_nationkey = N1.n_nationkey
    JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
    WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1994%'
    GROUP BY forSupp),
    
    (SELECT N1.n_name AS localSupp, N2.n_name AS forCust, count(l_linenumber) AS numCustItems 
    FROM nation N1, nation N2 
    JOIN orders ON o_orderkey = l_orderkey
    JOIN lineitem ON l_suppkey = s_suppkey
    JOIN supplier ON s_nationkey = N1.n_nationkey
    JOIN customer ON c_custkey = o_custkey AND c_nationkey = N2.n_nationkey
    WHERE N1.n_name <> N2.n_name AND l_shipdate LIKE '1994%'
    GROUP BY forCust)
WHERE forCust = forSupp;
