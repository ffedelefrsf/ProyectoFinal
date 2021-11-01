import { Servicio } from './servicio';
import { Usuario } from './usuario';

export interface Venta {
    id?: number,
    nroVenta?: number,
    descripcion?: string,
    servicio?: Servicio,
    usuarioModifica?: Usuario,
}