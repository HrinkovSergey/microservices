DROP DATABASE IF EXISTS test_database;

CREATE SCHEMA IF NOT EXISTS test_database DEFAULT CHARACTER SET UTF8MB4;

USE test_database;

-- -----------------------------------------------------
-- Table `locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS location (
  id      INT         NOT NULL AUTO_INCREMENT,
  country VARCHAR(45) NOT NULL,
  city    VARCHAR(45) NOT NULL UNIQUE,
PRIMARY KEY pk_locations (id)
);