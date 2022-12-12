SELECT 
    c_mktsegment,
    sum(c_acctbal)
FROM customer
WHERE c_mktsegment = 'BUILDING';
SELECT 
    c_mktsegment,
    sum(c_acctbal)
FROM customer
WHERE c_mktsegment = 'AUTOMOBILE';
SELECT 
    c_mktsegment,
    sum(c_acctbal)
FROM customer
WHERE c_mktsegment = 'HOUSEHOLD';
SELECT 
    c_mktsegment,
    sum(c_acctbal)
FROM customer
WHERE c_mktsegment = 'FURNITURE';
SELECT 
    c_mktsegment,
    sum(c_acctbal)
FROM customer
WHERE c_mktsegment = 'MACHINERY';