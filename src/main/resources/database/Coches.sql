CREATE DATABASE Coches;

USE Coches;

CREATE TABLE Coche (
                       matricula VARCHAR(20) PRIMARY KEY,
                       marca VARCHAR(20) DEFAULT NULL,
                       modelo VARCHAR(20) DEFAULT NULL,
                       tipo VARCHAR(20) DEFAULT NULL
);

CREATE TABLE Multas (
                        id_multas integer unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        precio_multa DECIMAL(10, 2) DEFAULT NULL,
                        fecha_multa DATE DEFAULT NULL,
                        matricula VARCHAR(20)
);

INSERT INTO Multas (matricula, precio_multa, fecha_multa)
VALUES ('1234ABC', 200, '2023-12-12'),
       ('1234ABC', 700, '2023-12-13'),
       ('1234ABC', 50, '2023-12-14');