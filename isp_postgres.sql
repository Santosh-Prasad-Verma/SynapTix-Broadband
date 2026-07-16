-- PostgreSQL schema dump for ISP Management System

--
-- Table structure for table admin
--
DROP TABLE IF EXISTS admin CASCADE;
CREATE TABLE admin (
  id SERIAL PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

-- Seed admin
INSERT INTO admin (username, password) VALUES ('admin', 'admin');

--
-- Table structure for table complain
--
DROP TABLE IF EXISTS complain CASCADE;
CREATE TABLE complain (
  ID SERIAL PRIMARY KEY,
  Complaint varchar(255) NOT NULL,
  Date varchar(30) DEFAULT NULL,
  Action varchar(255) DEFAULT NULL
);

-- Seed complain
INSERT INTO complain (Complaint, Date, Action) VALUES
('not internet in vashi', '2018-10-21', 'Solved'),
('not int in pune 404', '2018-10-21', 'Solved'),
('607', '2018-10-21', 'Solved');

--
-- Table structure for table customer
--
DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE customer (
  ID int NOT NULL PRIMARY KEY,
  Name varchar(255) NOT NULL,
  Contact varchar(255) NOT NULL,
  Address varchar(255) NOT NULL,
  Plan varchar(255) DEFAULT NULL,
  Sex varchar(255) NOT NULL,
  Purpose varchar(255) NOT NULL,
  email varchar(255) DEFAULT ''
);

-- Seed customer
INSERT INTO customer (ID, Name, Contact, Address, Plan, Sex, Purpose, email) VALUES
(11, 'nitish', '775z5df', 'csd', '10net', 'MALE', 'BUSINESS', 'nitish@example.com'),
(12, 'rajas', '465165', 'dsfds', '100net', 'MALE', 'INDIVIDUAL', 'rajas@example.com');

--
-- Table structure for table employee
--
DROP TABLE IF EXISTS employee CASCADE;
CREATE TABLE employee (
  ID int NOT NULL PRIMARY KEY,
  Name varchar(255) NOT NULL,
  Contact varchar(255) NOT NULL,
  JoinDate varchar(255) NOT NULL,
  Address varchar(255) NOT NULL,
  LeaveDate varchar(255) DEFAULT NULL
);

--
-- Table structure for table plan
--
DROP TABLE IF EXISTS plan CASCADE;
CREATE TABLE plan (
  ID int NOT NULL PRIMARY KEY,
  PlanName varchar(255) NOT NULL,
  Cost int NOT NULL,
  Speed varchar(25) NOT NULL,
  Duration varchar(255) NOT NULL
);

-- Seed plan
INSERT INTO plan (ID, PlanName, Cost, Speed, Duration) VALUES
(10, '10net', 110, '10', '6'),
(100, '100net', 10000, '100', '10');
