import { Localidad } from './localidad';
import { Usuario } from './usuario';

export interface Cobrador{
    id?: number,
    dni?: number,
    apellido?: string,
    nombre?: string,
    direccion?: string,
    telefono?: string,
    email?: string,
    sexo?: string,
    fechaNacimiento?: Date,
    localidad?: Localidad,
    usuarioModifica?: Usuario
}