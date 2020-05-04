import { Provincia } from './provincia';
import { Usuario } from './usuario';

export interface Localidad{
    id?: number,
    nroLocalidad: number,
    nombre?: string,
    codigoPostal?: number,
    provincia?: Provincia,
    usuarioModifica?: Usuario
}