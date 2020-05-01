import { Usuario } from './usuario';
import { Tarifa } from './tarifa';

export interface RangoTarifa{
    id?: number,
    edadDesde?: number,
    edadHasta?: number,
    valor?: number,
    tarifa?: Tarifa,
    usuarioModifica?: Usuario,
}