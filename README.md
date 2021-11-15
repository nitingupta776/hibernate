# hibernate

This project demonstrates modeling of one-to-many relationship between a Customer and one or more Services that Customer has registered for, using java Hibernate and Oracle database. It also provide support for UUID, Date and BLOB data types. I have used annotations (as opposed to XML based mapping).

The Customer class can have properties such as ID, Name, Address, DOB, Picture whereas Service class can have properties such as UUID, Name, Rate.

In addition to demonstrating creation, update and delete of instances, it show how queries such as "find all services of customer named Jill" and "Find all schedules of  employee named John" are expressed when hibernate ORM is in use.     

1. Create tables in the Oracle 11g Express database using this SQL query as follows:
drop table customer;
create table customer (uuid raw, name varchar(20), address varchar(20), dob date, picture blob);
drop table service;
create table service (id int, customer_id int, servicename varchar(20), rate int);

2. Change directory to the hibernate folder and compile the Customer, Service and ManageCustomerServices java files as follows (all the needed jar files and the hibernate.cfg.xml file is in that folder):

if you are on Mac, run the following commans in Terminal. If you are using an IDE, for example Eclipse or IntelliJ, you can just add these dependecies over there:
Please note, if you are on Windows, replace the colons (;) with the semicolons (;) for these statements

javac -classpath .:hibernate-annotations.jar:hibernate-commons-annotations.jar:ejb3-persistence.jar:hibernate-core.jar:ojdbc6.jar:dom4j.jar:slf4j-api-1.6.0.jar:commons-collections-3.2.2.jar:javassist.jar:jta-1.1.jar:antlr-2.7.6.jar Customer.java

3. Run ManageCustomer application as follows:

java -classpath .:hibernate-annotations.jar:hibernate-commons-annotations.jar:ejb3-persistence.jar:hibernate-core.jar:ojdbc6.jar:dom4j.jar:slf4j-api-1.6.0.jar:commons-collections-3.2.2.jar:javassist.jar:jta-1.1.jar:antlr-2.7.6.jar ManageCustomerServices
