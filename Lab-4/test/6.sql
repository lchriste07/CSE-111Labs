SELECT s_name, o_orderpriority, count(DISTINCT l_partkey)
FROM supplier
JOIN partsupp ON ps_suppkey = s_suppkey
JOIN part ON p_partkey = ps_partkey
JOIN lineitem ON l_suppkey = s_suppkey
JOIN orders ON o_orderkey = l_orderkey
JOIN nation ON n_nationkey = s_nationkey
WHERE n_nationkey = 3
GROUP BY s_name, o_orderpriority;