SELECT R1.r_name, R2.r_name, max(o_totalprice)
FROM region R1, region R2, nation N1, nation N2, orders, supplier, lineitem, customer
WHERE R1.r_regionkey = N1.n_regionkey AND R2.r_regionkey = N2.n_regionkey AND
    N1.n_nationkey = s_nationkey AND N2.n_nationkey = c_nationkey AND
    o_custkey = c_custkey AND o_orderkey = l_orderkey AND
    l_suppkey = s_suppkey
GROUP BY R1.r_name, R2.r_name;