import { Usuario } from './usuario';

export interface Plan{
    id?: number,
    descripcion?: string,
    usuarioModifica?: Usuario,
}