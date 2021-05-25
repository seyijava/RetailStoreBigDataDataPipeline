CREATE KEYSPACE retailSales WITH REPLICATION = 
{ 'class' : 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '2' } 
AND DURABLE_WRITES = true;

CREATE TABLE if not  exists retailsales.dailySales(
  storeCode text,
  city text,
  postalCode text,
  state text,
  productCode text,
  quantity int,
  unitAmount double,
  totalAmount double,
  transactionDate timestamp,
  salemonth int,
  saleyear int,
  PRIMARY KEY ((saleMonth, saleYear),transactionDate));

drop table retailsales.dailySales


customer_bought_product (
    store_id uuid,
    product_id text,
    order_time timestamp,
    email text,
    first_name text,
    last_name text,
    PRIMARY KEY ((store_id, product_id), order_time, email)

