import { Localidad } from './localidad';
import { Usuario } from './usuario';

export interface ParametroEmpresa{
    id?: number,
    nombre?: string,
    direccion?: string,
    cuit?: number,
    inicioActividades?: Date,
    condicionIva?: string,
    condicionIibb?: string,
    telefono?: string,
    email?: string,
    localidad?: Localidad,
    usuarioModifica?: Usuario,
}