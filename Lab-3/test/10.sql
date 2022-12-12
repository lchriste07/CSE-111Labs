SELECT sum(o_totalprice)
FROM orders, customer, nation, region
WHERE c_custkey = o_custkey
    AND n_nationkey = c_nationkey 
    AND r_regionkey = n_regionkey
    AND r_regionkey = 2 AND o_orderdate LIKE '1997-___%';