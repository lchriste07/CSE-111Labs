SELECT count(DISTINCT s_suppkey)
FROM supplier
JOIN partsupp ON ps_suppkey = s_suppkey
JOIN part ON ps_partkey = p_partkey
WHERE p_type LIKE '%POLISHED%' AND 
    (p_size = 3 OR p_size = 23 OR p_size = 36 OR p_size = 49);