DELIMITER $$
	CREATE TRIGGER COBERTURA_EDADES_TRIGGER_UPDATE
	AFTER UPDATE ON COBERTURA_EDADES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_EDADES_AUDIT`(`FECHA`,`ID`,`EDAD_DESDE`,`EDAD_HASTA`,`INDICADOR`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.EDAD_DESDE, NEW.EDAD_HASTA, NEW.INDICADOR, 'UPDATE');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COBERTURA_EDADES_TRIGGER_INSERT
	AFTER INSERT ON COBERTURA_EDADES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_EDADES_AUDIT`(`FECHA`,`ID`,`EDAD_DESDE`,`EDAD_HASTA`,`INDICADOR`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.EDAD_DESDE, NEW.EDAD_HASTA, NEW.INDICADOR, 'INSERT');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COBERTURA_EDADES_TRIGGER_DELETE
	AFTER DELETE ON COBERTURA_EDADES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COBERTURA_EDADES_AUDIT`(`FECHA`,`ID`,`EDAD_DESDE`,`EDAD_HASTA`,`INDICADOR`,`METODO`)
	  VALUES (NOW(), OLD.ID, OLD.EDAD_DESDE, OLD.EDAD_HASTA, OLD.INDICADOR, 'DELETE');
	END$$
DELIMITER ;