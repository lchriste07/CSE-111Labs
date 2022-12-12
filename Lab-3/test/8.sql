SELECT s_name, s_acctbal
FROM supplier, nation
WHERE s_acctbal >= 7000 AND n_nationkey = s_nationkey AND n_regionkey = 3;