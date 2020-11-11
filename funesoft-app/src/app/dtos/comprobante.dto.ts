import { Socio } from '@app/model/socio';

export interface ComprobanteDTO{
    id: number,
    nroComprobante: number,
    importeTotal: number,
    impreso: boolean,
    socio: Socio,
    pagado: boolean
}