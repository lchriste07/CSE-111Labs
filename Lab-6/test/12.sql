SELECT n_name
FROM nation
JOIN customer ON c_nationkey = n_nationkey
JOIN orders ON o_custkey = c_custkey
GROUP BY n_name
ORDER BY sum(o_totalprice)
LIMIT 1;