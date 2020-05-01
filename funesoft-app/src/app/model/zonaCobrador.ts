import { Zona } from './zona';
import { Cobrador } from './cobrador';
import { Usuario } from './usuario';

export interface ZonaCobrador{
    id?: number,
    zona?: Zona,
    cobrador?: Cobrador,
    usuarioModifica?: Usuario
}