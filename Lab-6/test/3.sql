SELECT count(p_partkey)
FROM part
JOIN partsupp S1 ON ps_partkey = p_partkey
JOIN supplier ON s_suppkey = ps_suppkey
JOIN nation ON n_nationkey = s_nationkey
WHERE n_name = 'UNITED STATES' AND S1.ps_suppkey IN (
    SELECT ps_suppkey
    FROM partsupp
    JOIN supplier ON s_suppkey = ps_suppkey
    JOIN part ON p_partkey = S1.ps_partkey
    JOIN nation ON n_nationkey = s_nationkey
    WHERE n_name = 'UNITED STATES' AND S1.ps_partkey = ps_partkey
    GROUP BY p_partkey
    HAVING count(p_partkey) = 2
);
