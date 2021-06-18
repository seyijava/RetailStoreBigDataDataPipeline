CREATE table if not exists retailsales.dailysalespercategorytbl (
    "storeCode" text,
    category text,
    "transactionDate" timestamp,
    city text,
    country text,
    state text,
    "totalPerCategory" int,
    "totalSales" double,
    PRIMARY KEY (("storeCode", category), "transactionDate")
) WITH CLUSTERING ORDER BY ("transactionDate" ASC)


CREATE table if not exists retailsales.dailySalesPerStoretbl (
    "storeCode" text,
    "transactionDate" timestamp,
    city text,
    country text,
    state text,
    "totalPerStore" int,
    "totalSales" double,
    PRIMARY KEY ("storeCode", "transactionDate")
) WITH CLUSTERING ORDER BY ("transactionDate" ASC)


CREATE table if not exists retailsales.topperformingcategorydailysales (
    "storeCode" text,
    "transactionDate" timestamp,
    city text,
    country text,
    state text,
    category text,
    "totalRevenue" double,
    PRIMARY KEY (("storeCode", category),"transactionDate")
) WITH CLUSTERING ORDER BY ("transactionDate" ASC)
