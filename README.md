# thefoodpanda

## Requirements
  The source directory was named as 'thefoodpanda'.
  It's run on intellij idea 2018.3.4 with jdk 11.0.1 
  & ojdbc8-21.1.0.0 driver.
  
## Creating 3 Tables,1 Trigger,1 Function

- CREATE TABLE persons
( 
	userid VARCHAR2(300) not null,
	username VARCHAR2(300) not null,
        password VARCHAR2(300) not null,
	PRIMARY KEY (userid)
);

- CREATE TABLE products
( 
	id number(10),
	name varchar2(50) not null,
	price number(10,2),
	description varchar2(200),
	PRIMARY KEY (id)
);

- CREATE TABLE foodorder
( 
	orderid number(10) not null,
	patronid varchar2(300) not null,
	orderdate date,
	contact varchar2(300),
        bill number(10,2),
        details varchar2(300),
	PRIMARY KEY (orderid)
);

- CREATE SEQUENCE foodorder_SEQ;

- CREATE OR REPLACE TRIGGER foodorder_TRG 
BEFORE INSERT ON foodorder 
FOR EACH ROW
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF INSERTING AND :NEW.orderid IS NULL THEN
      SELECT foodorder_seq.NEXTVAL INTO :NEW.orderid FROM SYS.DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END;
/


- CREATE OR REPLACE FUNCTION func_get_bill (itemid in NUMBER, item_count in NUMBER) return NUMBER IS
    item_price NUMBER(10,2);
    total_amount NUMBER(10,2);				
BEGIN
    SELECT PRICE INTO item_price FROM PRODUCTS
		WHERE itemid = ID;
		total_amount := item_price * item_count;
		return total_amount;
end;

## Other tasks
- We've to change the Oracle database name, username and password in the OracleConnect.java file accordingly.
- All the Queries were written on DataQuery.java file.
- Finally we've to run the FoodPandaMain.java file.
## Video Link
youtube demo link : https://www.youtube.com/watch?v=0P56Er-ye5c&t=1s
