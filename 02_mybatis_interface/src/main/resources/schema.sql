DROP DATABASE IF EXISTS db_boot2;
CREATE DATABASE IF NOT EXISTS db_boot2;
USE db_boot2;

DROP TABLE IF EXISTS tbl_board;
CREATE TABLE IF NOT EXISTS tbl_board
(
	board_id INT AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  contents VARCHAR(100),
  create_dt DATETIME,
  CONSTRAINT pk_board PRIMARY KEY (board_id)
) Engine=InnoDB;
