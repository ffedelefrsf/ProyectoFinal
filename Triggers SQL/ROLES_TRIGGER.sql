DELIMITER $$
	CREATE TRIGGER ROLES_TRIGGER_UPDATE
	AFTER UPDATE ON ROLES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ROLES_AUDIT`(`FECHA`,`ID`,`NOMBRE`,`ID_USUARIO_MODIFICA`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.NOMBRE, NEW.ID_USUARIO_MODIFICA, 'UPDATE');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER ROLES_TRIGGER_INSERT
	AFTER INSERT ON ROLES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ROLES_AUDIT`(`FECHA`,`ID`,`NOMBRE`,`ID_USUARIO_MODIFICA`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.NOMBRE, NEW.ID_USUARIO_MODIFICA, 'INSERT');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER ROLES_TRIGGER_DELETE
	AFTER DELETE ON ROLES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `ROLES_AUDIT`(`FECHA`,`ID`,`NOMBRE`,`ID_USUARIO_MODIFICA`,`METODO`)
	  VALUES (NOW(), OLD.ID, OLD.NOMBRE, OLD.ID_USUARIO_MODIFICA, 'DELETE');
	END$$
DELIMITER ;