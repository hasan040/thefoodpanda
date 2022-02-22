# thefoodpanda

1. The source directory was named as 'thefoodpanda'.
   It's run on intellij idea 2018.3.4 with jdk 11.0.1 
   & ojdbc8-21.1.0.0 driver.

2. To run this project we've to create 3 tables:
   persons,products,foodorder.

CREATE TABLE persons
( 
	userid VARCHAR2(300) not null,
	username VARCHAR2(300) not null,
        password VARCHAR2(300) not null,
	PRIMARY KEY (userid)
);

CREATE TABLE products
( 
	id number(10),
	name varchar2(50) not null,
	price number(10,2),
	description varchar2(200),
	PRIMARY KEY (id)
);

CREATE TABLE foodorder
( 
	orderid number(10) not null,
	patronid varchar2(300) not null,
	orderdate date,
	contact varchar2(300),
        bill number(10,2),
        details varchar2(300),
	PRIMARY KEY (orderid)
);


3. We've to change the Oracle database name, username and password in the OracleConnect.java file accordingly.

4. Finally we've to run the FoodPandaMain.java file.
5. youtube demo link : https://www.youtube.com/watch?v=0P56Er-ye5c&t=1s
