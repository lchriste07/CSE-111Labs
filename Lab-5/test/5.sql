SELECT s_name, p_size, min(ps_supplycost)
FROM supplier
JOIN partsupp ON ps_suppkey = s_suppkey
JOIN nation ON n_nationkey = s_nationkey
JOIN region on r_regionkey = n_regionkey
JOIN part ON p_partkey = ps_partkey
WHERE r_regionkey = 2 AND p_type LIKE '%STEEL%'
GROUP BY p_size
ORDER BY s_name, p_size;