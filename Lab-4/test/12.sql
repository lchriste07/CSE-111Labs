SELECT n_name, max(s_acctbal)
FROM supplier
JOIN nation on n_nationkey = s_nationkey
WHERE s_acctbal > 9000
GROUP BY n_name;