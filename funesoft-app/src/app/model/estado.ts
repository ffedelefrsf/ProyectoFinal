import { Usuario } from './usuario';

export interface Estado{
    id?: number,
    nroEstado?: number,
    descripcion?: string,
    usuarioModifica?: Usuario
}