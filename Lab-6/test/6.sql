SELECT s_name, c_name, min(o_totalprice)
FROM orders
JOIN customer ON c_custkey = o_custkey
JOIN lineitem ON l_orderkey = o_orderkey
JOIN supplier ON s_suppkey = l_suppkey