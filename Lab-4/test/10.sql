SELECT p_type, min(l_discount), max(l_discount)
FROM part
JOIN lineitem ON l_partkey = p_partkey
WHERE p_type LIKE '%ECONOMY%' AND p_type LIKE '%COPPER%'
GROUP BY p_type;