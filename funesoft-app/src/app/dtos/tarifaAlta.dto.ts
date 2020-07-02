import { Plan } from '@app/model/plan';
import { RangosTarifasAltaComponent } from '@app/components/rangos-tarifa/rangos-tarifas-alta/rangos-tarifas-alta.component';
import { RangoTarifa } from '@app/model/rangoTarifa';

export interface TarifaAltaDTO{
    nroTarifa?: number,
    descripcion?: string,
    valor?: number,
    plan?: Plan,
    listRango?: RangoTarifa[]
}