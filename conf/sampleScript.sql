CREATE DATABASE exampleDB;
use exampleDB;
CREATE TABLE exampleDB.cricketers_data(
   First_Name VARCHAR(255),
   Last_Name VARCHAR(255),
   Date_Of_Birth date,
   Place_Of_Birth VARCHAR(255),
   Country VARCHAR(255)
);
insert into cricketers_data values('Shikhar', 'Dhawan', DATE('1981-12-05'), 'Delhi', 'India');
insert into cricketers_data values('Jonathan', 'Trott', DATE('1981-04-22'), 'CapeTown', 'SouthAfrica');
insert into cricketers_data values('Kumara', 'Sangakkara', DATE('1977-10-27'), 'Matale', 'Srilanka');
insert into cricketers_data values('Virat', 'Kohli', DATE('1988-11-05'), 'Delhi', 'India');
insert into cricketers_data values('Rohit', 'Sharma', DATE('1987-04-30'), 'Nagpur', 'India');
select * from mydatabase.cricketers_data;