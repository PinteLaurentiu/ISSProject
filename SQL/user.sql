CREATE DATABASE iss;

CREATE TABLE user
(
  idUser      INT AUTO_INCREMENT
    PRIMARY KEY,
  password    VARCHAR(50)  NOT NULL,
  nume        VARCHAR(50)  NOT NULL,
  prenume     VARCHAR(50)  NOT NULL,
  birthday    DATETIME     NOT NULL,
  localitate  VARCHAR(50)  NOT NULL,
  judet       VARCHAR(50)  NOT NULL,
  resedinta   VARCHAR(200) NOT NULL,
  localitate2 VARCHAR(50)  NOT NULL,
  judet2      VARCHAR(50)  NOT NULL,
  domiciliu   VARCHAR(200) NOT NULL,
  email       VARCHAR(50)  NOT NULL,
  telefon     VARCHAR(20)  NOT NULL
);

CREATE PROCEDURE login(IN emailVar VARCHAR(100), IN pass VARCHAR(100))
  BEGIN
    SELECT COUNT(*) FROM user
      WHERE email = emailVar AND password = SHA1(pass);
  END;

CREATE PROCEDURE setPassword(IN id INT, IN pass VARCHAR(100))
  BEGIN 
    UPDATE user SET password = SHA1(pass) WHERE idUser = id;
  END;