SELECT count(s_suppkey)
FROM (SELECT s_suppkey FROM nation
    JOIN supplier ON s_suppkey = l_suppkey
    JOIN lineitem ON l_orderkey = o_orderkey
    JOIN orders ON o_custkey = c_custkey
    JOIN customer ON c_nationkey = n_nationkey
    WHERE n_name = 'GERMANY' OR n_name = 'FRANCE'
    GROUP BY s_suppkey
    HAVING count(DISTINCT o_orderkey) < 50);