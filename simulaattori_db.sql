-- Drop the database and user if they exist
DROP DATABASE IF EXISTS simulaattori_db;
DROP USER IF EXISTS 'appuser'@'localhost';

-- Create the database
CREATE DATABASE IF NOT EXISTS simulaattori_db;
USE simulaattori_db;

-- Create the 'simuloinnit' table
CREATE TABLE simuloinnit (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    kokonaisaika DECIMAL(10,2) NOT NULL,
    palveltu INT(11) NOT NULL,
    bernoulliArrival DOUBLE NOT NULL,
    bernoulliRedirect DOUBLE NOT NULL,
    inputAika DOUBLE NOT NULL,
    inputViive BIGINT(20) NOT NULL
);

-- Create the 'palveluaika_ika' table
CREATE TABLE palveluaika_ika (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    lowAge DECIMAL(10,2),
    middleAge DECIMAL(10,2),
    highAge DECIMAL(10,2)
);

-- Create a template for the other tables
CREATE TABLE pakettiautomaatti (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jonossa INT(10),
    palveltu INT(10),
    keskimjonoaika DECIMAL(10,2),
    keskimpalveluaika DECIMAL(10,2),
    kokonaisaika DECIMAL(10,2),
    distribuutio VARCHAR(255) NOT NULL,
    mean DOUBLE NOT NULL,
    var DOUBLE NOT NULL
);

CREATE TABLE palvelunvalinta LIKE pakettiautomaatti;
CREATE TABLE noutolaheta LIKE pakettiautomaatti;
CREATE TABLE erityistapaukset LIKE pakettiautomaatti;

-- Grant permissions to the 'appuser' user
CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'simulaattori';
GRANT SELECT, INSERT, UPDATE, DELETE, DROP ON simulaattori_db.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
