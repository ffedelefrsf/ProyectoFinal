import { Localidad } from '@app/model/localidad';

export interface CobradorDTO{
    dni?: number,
    apellido?: string,
    nombre?: string,
    direccion?: string,
    telefono?: string,
    email?: string,
    sexo?: string,
    fechaNacimiento?: Date,
    idLocalidad?: number
}