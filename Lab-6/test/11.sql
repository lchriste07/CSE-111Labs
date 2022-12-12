SELECT n_name
FROM nation
JOIN customer ON c_nationkey = n_nationkey
GROUP BY n_name
ORDER BY count(c_custkey)
LIMIT 1;