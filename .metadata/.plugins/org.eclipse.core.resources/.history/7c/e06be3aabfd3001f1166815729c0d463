DROP DATABASE IF EXISTS db_board;
CREATE DATABASE IF NOT EXISTS db_board;
USE db_board;

DROP TABLE IF EXISTS tbl_board;
CREATE TABLE IF NOT EXISTS tbl_board
(
	board_id INT AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  contents VARCHAR(100),
  create_dt DATETIME,
  CONSTRAINT pk_board PRIMARY KEY (board_id)
) Engine=InnoDB;
