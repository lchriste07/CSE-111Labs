SELECT r_name, s_name, max(s_acctbal)
FROM supplier
JOIN nation on n_nationkey = s_nationkey
JOIN region on r_regionkey = n_regionkey
GROUP BY r_name;