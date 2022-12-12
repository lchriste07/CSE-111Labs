SELECT count(ps_suppkey)
FROM (SELECT ps_suppkey 
    FROM partsupp
    JOIN supplier ON s_suppkey = ps_suppkey
    JOIN nation ON n_nationkey = s_nationkey
    JOIN part ON p_partkey = ps_partkey
    WHERE n_name = 'UNITED STATES'
    GROUP BY ps_suppkey
    HAVING count(DISTINCT ps_partkey) > 40);