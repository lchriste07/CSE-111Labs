SELECT r_name, count(s_suppkey)
FROM supplier
JOIN nation ON n_nationkey = s_nationkey
JOIN region ON r_regionkey = n_regionkey
GROUP BY r_name;