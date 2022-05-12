-- -----------------------------------------------------
-- Table `company`
-- -----------------------------------------------------
CREATE TABLE company (
  id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   name        VARCHAR(255),
   location_id BIGINT,
   CONSTRAINT pk_company PRIMARY KEY (id)
);