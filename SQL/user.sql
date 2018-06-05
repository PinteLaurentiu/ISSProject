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
    idTransfer int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    idPungaSange int NOT NULL,
    `from` varchar(100) NOT NULL,
    `to` varchar(100) NOT NULL,
    CONSTRAINT transfer_pungasange_FK FOREIGN KEY (idPungaSange) REFERENCES pungaSange (idSange) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE UNIQUE INDEX transfer_idTransfer_uindex ON transfer (idTransfer);

CREATE TABLE cerere
(
  idCerere INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  numePacient VARCHAR(100) NOT NULL,
  prenumePacient VARCHAR(100) NOT NULL,
  locatie VARCHAR(100) NOT NULL,
  componentaSange INT NOT NULL,
  status INT NOT NULL,
  grupaSange INT NOT NULL,
  gradDeUrgenta INT NOT NULL,
  cantitatea INT DEFAULT 1 NOT NULL,
  cantitateDonata INT DEFAULT 0 NOT NULL
);
-- TODO : stergeti fostul tabel cerere, si rulati-l pe acesta!
CREATE UNIQUE INDEX Cerere_idCerere_uindex ON cerere (idCerere);


CREATE TABLE pungaSange
(
  idSange INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  idDonare INT NOT NULL,
  tip INT NOT NULL,
  locatie VARCHAR(100) NOT NULL,
  CONSTRAINT pungaSange_donare_idDonare_fk FOREIGN KEY (idDonare) REFERENCES donare (idDonare)
);

CREATE TABLE analiza
(
  idAnaliza INT PRIMARY KEY NOT NULL,
  imunoHematologice VARCHAR(100),
  grupaSange INT NOT NULL,
  boliTransmisibile VARCHAR(100),
  CONSTRAINT analiza_donare_idDonare_fk FOREIGN KEY (idAnaliza) REFERENCES donare (idDonare)
);

CREATE TABLE componentaSange
(
  idComponenta INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  idDonare INT NOT NULL,
  dataExpirare DATETIME NOT NULL,
  locatia VARCHAR(100),
  tip INT NOT NULL,
  CONSTRAINT componentaSange_donare_idDonare_fk FOREIGN KEY (idDonare) REFERENCES donare (idDonare)
#     ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE probaCombalitateMajora
(
  idComponentaSange INT NOT NULL,
  idCerere INT NOT NULL,
  acceptat BIT NOT NULL,
  CONSTRAINT probaCombalitateMajora_idComponentaSange_idCerere_pk PRIMARY KEY (idComponentaSange, idCerere),
  CONSTRAINT probaCombalitateMajora_cerere_idCerere_fk FOREIGN KEY (idCerere) REFERENCES cerere (idCerere),
  CONSTRAINT probaCombalitateMajora_componentasange_idComponenta_fk FOREIGN KEY (idComponentaSange) REFERENCES componentaSange (idComponenta)
);

CREATE TABLE transferComponenta
(
  idTransfer INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  idComponenta INT NOT NULL,
  `from` VARCHAR(100) NOT NULL,
  `to` VARCHAR(100) NOT NULL,
  CONSTRAINT transferComponenta_componentasange_idComponenta_fk FOREIGN KEY (idComponenta) REFERENCES componentaSange (idComponenta) ON DELETE CASCADE ON UPDATE CASCADE
);