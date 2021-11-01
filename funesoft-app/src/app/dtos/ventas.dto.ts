import { Localidad } from '@app/model/localidad';
import { Servicio } from '@app/model/servicio';

export interface VentasDTO{
    id?: number,
    nroVenta?: number,
    descripcion?: string,
    servicio?: Servicio,
    fecha?: String,
}