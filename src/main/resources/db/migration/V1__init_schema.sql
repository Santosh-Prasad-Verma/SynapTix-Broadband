-- PostgreSQL schema migration for ISP Management System

-- Table structure for table admin
CREATE TABLE IF NOT EXISTS admin (
  id SERIAL PRIMARY KEY,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL
);

-- Seed admin
INSERT INTO admin (username, password) VALUES ('admin', 'admin')
ON CONFLICT DO NOTHING;

-- Table structure for table complain
CREATE TABLE IF NOT EXISTS complain (
  ID SERIAL PRIMARY KEY,
  Complaint varchar(255) NOT NULL,
  Date varchar(30) DEFAULT NULL,
  Action varchar(255) DEFAULT NULL
);

-- Seed complain
INSERT INTO complain (ID, Complaint, Date, Action) VALUES
(1, 'not internet in vashi', '2018-10-21', 'Solved'),
(2, 'not int in pune 404', '2018-10-21', 'Solved'),
(3, '607', '2018-10-21', 'Solved')
ON CONFLICT (ID) DO NOTHING;

-- Table structure for table customer
CREATE TABLE IF NOT EXISTS customer (
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
(11, 'rahul', '775z5df', 'csd', '10net', 'MALE', 'BUSINESS', 'rahul@example.com'),
(12, 'rajas', '465165', 'dsfds', '100net', 'MALE', 'INDIVIDUAL', 'rajas@example.com')
ON CONFLICT (ID) DO NOTHING;

-- Table structure for table employee
CREATE TABLE IF NOT EXISTS employee (
  ID int NOT NULL PRIMARY KEY,
  Name varchar(255) NOT NULL,
  Contact varchar(255) NOT NULL,
  JoinDate varchar(255) NOT NULL,
  Address varchar(255) NOT NULL,
  LeaveDate varchar(255) DEFAULT NULL
);

-- Table structure for table plan
CREATE TABLE IF NOT EXISTS plan (
  ID int NOT NULL PRIMARY KEY,
  PlanName varchar(255) NOT NULL,
  Cost int NOT NULL,
  Speed varchar(25) NOT NULL,
  Duration varchar(255) NOT NULL
);

-- Seed plan
INSERT INTO plan (ID, PlanName, Cost, Speed, Duration) VALUES
(10, '10net', 110, '10', '6'),
(100, '100net', 10000, '100', '10')
ON CONFLICT (ID) DO NOTHING;
