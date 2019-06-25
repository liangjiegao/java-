CREATE DATABASE SkyDriver;

DROP TABLE IF EXISTS file ;
CREATE TABLE file(
                     id INT AUTO_INCREMENT,
                     file_path VARCHAR(200) NOT NULL,
                     TYPE VARCHAR(20) NOT NULL,
                     size INT NOT NULL,
                     DATETIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                     PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS user ;
CREATE TABLE user(
                     id INT AUTO_INCREMENT,
                     username VARCHAR(60) NOT NULL,
                     account VARCHAR(100) NOT NULL,
                     passwd VARCHAR(100) NOT NULL,
                     PRIMARY KEY (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS user_file ;
CREATE TABLE user_file(
                          id INT AUTO_INCREMENT,
                          user_id INT NOT NULL ,
                          file_id INT NOT NULL,
                          filename varchar(100) NOT NULL,
                          parent VARCHAR(10) NOT NULL,
                          link_id INT NOT NULL,
                          primary key (id),
                          foreign key (user_id) references user(id),
                          foreign key (file_id) references file(id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;



show variables like '%time_zone%';
set global time_zone='+8:00';

