DROP DATABASE IF EXISTS db_boot7;

CREATE DATABASE IF NOT EXISTS db_boot7;

USE db_boot7;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE IF NOT EXISTS tbl_user
(
	user_no       INT AUTO_INCREMENT,
  user_id       VARCHAR(100) NOT NULL UNIQUE,
  user_password VARCHAR(100),
  user_name     VARCHAR(100),
  user_role     VARCHAR(5) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (user_no)
)Engine=InnoDB;