import { Cobrador } from './cobrador';
import { Comprobante } from './comprobante';
import { Usuario } from './usuario';
import { Socio } from './socio';

export interface Pago{
    id?: number,
    valor?: number,
    socio?: Socio,
    cobrador?: Cobrador,
    comprobante?: Comprobante,
    usuarioModifica?: Usuario
}