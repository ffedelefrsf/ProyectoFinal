DELIMITER $$
	CREATE TRIGGER COMPROBANTES_TRIGGER_UPDATE
	AFTER UPDATE ON COMPROBANTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COMPROBANTES_AUDIT`(`FECHA`,`ID`,`NRO_COMPROBANTE`,`ID_USUARIO_MODIFICA`,`IMPORTE_TOTAL`,`ID_SOCIO`,`IMPRESO`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.NRO_COMPROBANTE, NEW.ID_USUARIO_MODIFICA, NEW.IMPORTE_TOTAL, NEW.ID_SOCIO, NEW.IMPRESO, 'UPDATE');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COMPROBANTES_TRIGGER_INSERT
	AFTER INSERT ON COMPROBANTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COMPROBANTES_AUDIT`(`FECHA`,`ID`,`NRO_COMPROBANTE`,`ID_USUARIO_MODIFICA`,`IMPORTE_TOTAL`,`ID_SOCIO`,`IMPRESO`,`METODO`)
	  VALUES (NOW(), NEW.ID, NEW.NRO_COMPROBANTE, NEW.ID_USUARIO_MODIFICA, NEW.IMPORTE_TOTAL, NEW.ID_SOCIO, NEW.IMPRESO, 'INSERT');
	END$$
DELIMITER ;

DELIMITER $$
	CREATE TRIGGER COMPROBANTES_TRIGGER_DELETE
	AFTER DELETE ON COMPROBANTES
	FOR EACH ROW
	BEGIN
	  INSERT INTO `COMPROBANTES_AUDIT`(`FECHA`,`ID`,`NRO_COMPROBANTE`,`ID_USUARIO_MODIFICA`,`IMPORTE_TOTAL`,`ID_SOCIO`,`IMPRESO`,`METODO`)
	  VALUES (NOW(), OLD.ID, OLD.NRO_COMPROBANTE, OLD.ID_USUARIO_MODIFICA, OLD.IMPORTE_TOTAL, OLD.ID_SOCIO, OLD.IMPRESO, 'DELETE');
	END$$
DELIMITER ;