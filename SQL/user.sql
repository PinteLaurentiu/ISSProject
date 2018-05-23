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

CREATE TABLE activations
(
  userId      INT              NOT NULL
    PRIMARY KEY,
  isActivated BIT DEFAULT b'0' NOT NULL,
  generatedId VARCHAR(100)     NOT NULL,
  CONSTRAINT activations_generatedId_uindex
  UNIQUE (generatedId),
  CONSTRAINT activations_user_idUser_fk
  FOREIGN KEY (userId) REFERENCES user (idUser)
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

CREATE PROCEDURE activate(IN id INT, IN uid VARCHAR(100), IN active BIT)
  BEGIN
    UPDATE activations SET isActivated = active WHERE userId = id and generatedId=uid;
  END;
