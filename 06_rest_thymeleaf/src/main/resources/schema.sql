DROP DATABASE IF EXISTS db_boot6;
CREATE DATABASE IF NOT EXISTS db_boot6;
USE db_boot6;

DROP TABLE IF EXISTS tbl_address;
DROP TABLE IF EXISTS tbl_member;
CREATE TABLE IF NOT EXISTS tbl_member
(
    mem_id     INT AUTO_INCREMENT,
    mem_email  VARCHAR(100) NOT NULL UNIQUE,
    mem_name   VARCHAR(100),
    mem_gender VARCHAR(2),
    CONSTRAINT pk_member PRIMARY KEY (mem_id)
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS tbl_address
(
    addr_id        INT AUTO_INCREMENT,
    postcode       VARCHAR(5),
    road_address   VARCHAR(100),
    jibun_address  VARCHAR(100),
    detail_address VARCHAR(100),
    extra_address  VARCHAR(100),
    mem_id         INT,
    addr_name      VARCHAR(100),
    CONSTRAINT pk_addr PRIMARY KEY (addr_id),
    CONSTRAINT fk_mem_addr FOREIGN KEY (mem_id)
        REFERENCES tbl_member (mem_id)
            ON DELETE CASCADE
) Engine=InnoDB;

ALTER TABLE tbl_member AUTO_INCREMENT = 1001;