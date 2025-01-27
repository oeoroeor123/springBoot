DROP DATABASE IF EXISTS db_boot5;
CREATE DATABASE IF NOT EXISTS db_boot5;
USE db_boot5;

DROP TABLE IF EXISTS tbl_user;
CREATE TABLE IF NOT EXISTS tbl_user
(
    user_id   INT AUTO_INCREMENT,
    email     VARCHAR(100) NOT NULL UNIQUE,
    pwd       VARCHAR(100),
    nickname  VARCHAR(100) NOT NULL,
    create_dt DATETIME,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
) Engine=InnoDB;
