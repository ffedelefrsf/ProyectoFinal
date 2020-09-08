export enum EndpointEnum{
    LOGIN = '/login',
    LOGOUT = '/logout',
    CHECK = '/check',
    CREATE_SOCIO = '/socio/insert',
    GET_SOCIOS = '/socio/get',
    EDIT_SOCIO = '/socio/update',
    DELETE_SOCIO = '/socio/delete',
    GET_PROVINCIAS = '/provincia/getAll',
    GET_LOCALIDADES = '/localidad/getAll',
    GET_NOMBRES_LOCALIDADES = '/localidad/getAllNombres',
    GET_ZONAS = '/zona/getAll',
    GET_OBRAS_SOCIALES = '/obraSocial/getAll',
    GET_PLANES = '/plan/getAll',
    GET_TARIFAS = '/tarifa/getAll',
    GET_MOTIVOS_BAJA = '/motivoBaja/getAll',
    CREATE_COMPROBANTES = '/comprobante/generarComprobantesMasivos',
    DESCARGAR_COMPROBANTES = '/comprobante/generarPDF',
    GET_RANGOS_TARIFA = '/rangoTarifa/getRango',
    GET_ENFERMEDADES = '/enfermedad/getAll',
    CREATE_ZONA = '/zona/insert',
    CREATE_ADHERENTE = '/adherente/insert',
    DELETE_ADHERENTE = '/adherente/delete',
    GET_ADHERENTES = '/adherente/getAll',
    GET_ADHERENTES_ORDERED = '/adherente/getAllOrderedBySocio',
    EDIT_ADHERENTE = '/adherente/update',
    CREATE_TARIFA = '/tarifa/insert',
    GET_COBRADORES = '/cobrador/getAll'
}