CREATE TABLE SOCIOS(
	id_socio INT(8) PRIMARY KEY AUTO_INCREMENT,
	dni INT(8) NOT NULL UNIQUE KEY NOT NULL,
	apellido VARCHAR(100) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	direccion VARCHAR(100) NOT NULL,
	telefono VARCHAR(10) NOT NULL,
	email VARCHAR(100) NOT NULL,
	sexo VARCHAR(50) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	usuario_alta INT(8) NOT NULL,
	saldo DOUBLE(11, 2) NOT NULL,
	id_tarifa INT(8) NOT NULL,
	id_zona INT(8) NOT NULL,
	id_localidad INT(8) NOT NULL,
	id_obra_social INT(8) NOT NULL
);

INSERT INTO SOCIOS 
(dni, 		apellido, nombre, 	direccion, 				telefono, 	email, 				sexo, 		fecha_nacimiento, 	usuario_alta, saldo, id_tarifa, id_zona, id_localidad, id_obra_social)
VALUES 
(40053701, 	'FEDELE', 'FAUSTO', 	'CANDIDO PUJATO 2754 PISO 9 DPTO B', 	'3404516973', 	'faustofedele2013@gmail.com', 	'Masculino', 	'1997-05-06', 		1,		0.00, 1,	1,	1,		1);