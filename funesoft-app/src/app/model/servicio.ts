import { Usuario } from './usuario';

export interface Servicio {
    id?: number,
    nroServicio?: number,
    descripcion?: string,
    valor?: number,
    usuarioModifica?: Usuario,
}