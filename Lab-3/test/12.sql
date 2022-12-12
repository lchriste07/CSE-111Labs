SELECT r_name, count(o_orderkey)
FROM orders, customer, region, nation
WHERE o_orderstatus = 'P' AND
    o_custkey = c_custkey AND
    c_nationkey = n_nationkey AND
    n_regionkey = r_regionkey
GROUP BY r_regionkey;
