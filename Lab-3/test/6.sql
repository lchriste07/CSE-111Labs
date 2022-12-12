SELECT n_name
FROM nation, orders, customer
WHERE (o_orderdate = '1994-09-09' OR o_orderdate = '1994-09-10' OR o_orderdate = '1994-09-11') 
    AND o_custkey = c_custkey AND c_nationkey = n_nationkey
GROUP BY n_name;