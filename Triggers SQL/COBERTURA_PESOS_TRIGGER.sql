DELIMITER $$
	CREATE TRIGGER COBERTURA_PESOS_TRIGGER_UPDATE
	AFTER UPDATE ON COBERTURA_PESOS
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_PESOS_AUDIT`(`FECHA`,`ID`,`DESCRIPCION`,`PESO`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.DESCRIPCION, NEW.PESO, 'UPDATE');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COBERTURA_PESOS_TRIGGER_INSERT
	AFTER INSERT ON COBERTURA_PESOS
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_PESOS_AUDIT`(`FECHA`,`ID`,`DESCRIPCION`,`PESO`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.DESCRIPCION, NEW.PESO, 'INSERT');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COBERTURA_PESOS_TRIGGER_DELETE
	AFTER DELETE ON COBERTURA_PESOS
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_PESOS_AUDIT`(`FECHA`,`ID`,`DESCRIPCION`,`PESO`,`METODO`)
	  VALUES (NOW(), OLD.ID, OLD.DESCRIPCION, OLD.PESO, 'DELETE');
	END$$
DELIMITER ;