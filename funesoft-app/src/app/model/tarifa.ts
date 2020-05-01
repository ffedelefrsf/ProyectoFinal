import { Plan } from './plan';

export interface Tarifa{
    id?: number,
    descripcion?: string,
    valor?: number,
    plan?: Plan,
}