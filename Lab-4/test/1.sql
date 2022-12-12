SELECT c_name, sum(o_totalprice)
FROM orders
JOIN customer ON c_custkey = o_custkey
WHERE c_nationkey = 6 AND o_orderdate LIKE '1995-___%'
GROUP BY c_name;