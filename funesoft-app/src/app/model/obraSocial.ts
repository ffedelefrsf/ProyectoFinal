import { Usuario } from './usuario';

export interface ObraSocial{
    id?: number,
    descripcion?: string,
    tiene_sepelio?: boolean,
    usuarioModifica?: Usuario
}