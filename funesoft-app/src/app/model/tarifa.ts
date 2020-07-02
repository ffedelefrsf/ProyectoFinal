import { Plan } from './plan';

export interface Tarifa{
    id?: number,
    nroTarifa?: number,
    descripcion?: string,
    valor?: number,
    plan?: Plan,
}