SELECT r_name, count(s_suppkey)
FROM supplier
JOIN nation ON n_nationkey = s_nationkey
JOIN region ON r_regionkey = n_regionkey
JOIN (
    SELECT r_name AS rtab, avg(s_acctbal) AS regional_avg
    FROM supplier
    JOIN nation ON n_nationkey = s_nationkey
    JOIN region ON r_regionkey = n_regionkey
    GROUP BY r_name
    ) ON rtab = r_name
WHERE s_acctbal < regional_avg
GROUP BY r_name;