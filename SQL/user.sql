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
delimiter //

CREATE PROCEDURE login(IN emailVar VARCHAR(100), IN pass VARCHAR(100))
  BEGIN
    SELECT COUNT(*) FROM user
      WHERE email = emailVar AND password = SHA1(pass);
  END;//

DELIMITER ;
DELIMITER //

CREATE PROCEDURE setPassword(IN id INT, IN pass VARCHAR(100))
  BEGIN
    UPDATE user SET password = SHA1(pass) WHERE idUser = id;
  END;//
DELIMITER ;

create table userroles
(
  idUser int not null,
  idRole int auto_increment
    primary key,
  role int not null,
  constraint userRoles_user_idUser_fk
  foreign key (idUser) references user (idUser)
)
  engine=InnoDB
;

create index userRoles_user_idUser_fk
  on userroles (idUser)
;


DELIMITER //
CREATE PROCEDURE activate(IN id INT, IN uid VARCHAR(100), IN active BIT)
  BEGIN
    UPDATE activations SET isActivated = active WHERE userId = id and generatedId=uid;
  END;



CREATE TABLE donare
(
  idDonare INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  centru INT NOT NULL,
  ora DATETIME NOT NULL,
  pentru VARCHAR(300),
  status INT,
  CONSTRAINT donare_spital_idSpital_fk FOREIGN KEY (centru) REFERENCES spital (idSpital)
);

CREATE TABLE spital
(
  idSpital INT AUTO_INCREMENT
    PRIMARY KEY,
  nume     VARCHAR(200) NOT NULL,
  CONSTRAINT spitale_nume_uindex
  UNIQUE (nume)
);


ALTER TABLE donare ADD idUser INT NOT NULL;
ALTER TABLE donare
ADD CONSTRAINT donare_user_idUser_fk
FOREIGN KEY (idUser) REFERENCES user (idUser) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE consult
(
  idConsult INT PRIMARY KEY NOT NULL,
  greutate FLOAT NOT NULL,
  tensiune FLOAT NOT NULL,
  puls INT NOT NULL,
  boliDepistate VARCHAR(500),
  inaltime FLOAT NOT NULL,
  apt BIT NOT NULL,
  CONSTRAINT consult_donare_idDonare_fk FOREIGN KEY (idConsult) REFERENCES donare (idDonare) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE transfer
(
    idTransfer int PRIMARY KEY NOT NULL,
    `from` varchar(20) NOT NULL,
    `to` varchar(20) NOT NULL
);
CREATE UNIQUE INDEX transfer_idTransfer_uindex ON transfer (idTransfer)