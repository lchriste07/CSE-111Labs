SELECT n_name, count(s_nationkey), min(s_acctbal)
FROM supplier, nation
WHERE n_nationkey = s_nationkey
GROUP BY n_name
HAVING count(s_nationkey) > 5;