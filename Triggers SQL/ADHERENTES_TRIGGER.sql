DELIMITER $$
	CREATE TRIGGER ADHERENTES_TRIGGER_UPDATE
	AFTER UPDATE ON ADHERENTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ADHERENTES_AUDIT`(`FECHA`,`ID`,`DNI`,`APELLIDO`,`NOMBRE`,`DIRECCION`,`TELEFONO`,`EMAIL`,`SEXO`,`FECHA_NACIMIENTO`,`FECHA_COBERTURA`,`SALDO`,`ID_SOCIO`,`ID_ZONA`,`ID_LOCALIDAD`,`ID_OBRA_SOCIAL`,`ID_ESTADO`,`ID_USUARIO_MODIFICA`,`METODO`,`ID_ENFERMEDAD`)
	  VALUES (NOW(), NEW.ID, NEW.DNI, NEW.APELLIDO, NEW.NOMBRE, NEW.DIRECCION, NEW.TELEFONO, NEW.EMAIL, NEW.SEXO, NEW.FECHA_NACIMIENTO, NEW.FECHA_COBERTURA, NEW.SALDO, NEW.ID_SOCIO, NEW.ID_ZONA, NEW.ID_LOCALIDAD, NEW.ID_OBRA_SOCIAL, NEW.ID_ESTADO, NEW.ID_USUARIO_MODIFICA, 'UPDATE', NEW.ID_ENFERMEDAD);
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER ADHERENTES_TRIGGER_INSERT
	AFTER INSERT ON ADHERENTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ADHERENTES_AUDIT`(`FECHA`,`ID`,`DNI`,`APELLIDO`,`NOMBRE`,`DIRECCION`,`TELEFONO`,`EMAIL`,`SEXO`,`FECHA_NACIMIENTO`,`FECHA_COBERTURA`,`SALDO`,`ID_SOCIO`,`ID_ZONA`,`ID_LOCALIDAD`,`ID_OBRA_SOCIAL`,`ID_ESTADO`,`ID_USUARIO_MODIFICA`,`METODO`,`ID_ENFERMEDAD`)
	  VALUES (NOW(), NEW.ID, NEW.DNI, NEW.APELLIDO, NEW.NOMBRE, NEW.DIRECCION, NEW.TELEFONO, NEW.EMAIL, NEW.SEXO, NEW.FECHA_NACIMIENTO, NEW.FECHA_COBERTURA, NEW.SALDO, NEW.ID_SOCIO, NEW.ID_ZONA, NEW.ID_LOCALIDAD, NEW.ID_OBRA_SOCIAL, NEW.ID_ESTADO, NEW.ID_USUARIO_MODIFICA, 'INSERT', NEW.ID_ENFERMEDAD);
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER ADHERENTES_TRIGGER_DELETE
	AFTER DELETE ON ADHERENTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ADHERENTES_AUDIT`(`FECHA`,`ID`,`DNI`,`APELLIDO`,`NOMBRE`,`DIRECCION`,`TELEFONO`,`EMAIL`,`SEXO`,`FECHA_NACIMIENTO`,`FECHA_COBERTURA`,`SALDO`,`ID_SOCIO`,`ID_ZONA`,`ID_LOCALIDAD`,`ID_OBRA_SOCIAL`,`ID_ESTADO`,`ID_USUARIO_MODIFICA`,`METODO`,`ID_ENFERMEDAD`)
	  VALUES (NOW(), OLD.ID, OLD.DNI, OLD.APELLIDO, OLD.NOMBRE, OLD.DIRECCION, OLD.TELEFONO, OLD.EMAIL, OLD.SEXO, OLD.FECHA_NACIMIENTO, OLD.FECHA_COBERTURA, OLD.SALDO, OLD.ID_SOCIO, OLD.ID_ZONA, OLD.ID_LOCALIDAD, OLD.ID_OBRA_SOCIAL, OLD.ID_ESTADO, OLD.ID_USUARIO_MODIFICA, 'DELETE', OLD.ID_ENFERMEDAD);
	END$$
DELIMITER ;