.mode "csv"
.separator "|"
.import ./data/customer.tbl customer
.import ./data/lineitem.tbl lineitem
.import ./data/nation.tbl nation
.import ./data/orders.tbl orders
.import ./data/part.tbl part
.import ./data/partsupp.tbl partsupp
.import ./data/region.tbl region
.import ./data/supplier.tbl supplier

SELECT * FROM customer;
SELECT * FROM lineitem;
SELECT * FROM nation;
SELECT * FROM orders;
SELECT * FROM part;
SELECT * FROM partsupp;
SELECT * FROM region;
SELECT * FROM supplier;