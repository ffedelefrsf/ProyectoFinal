import { Usuario } from './usuario';
import { Localidad } from './localidad';
import { ObraSocial } from './obraSocial';
import { Estado } from './estado';
import { Socio } from './socio';
import { Zona } from './zona';

export interface Adherente{
    id?: number,
    dni?: number,
    apellido?: string,
    nombre?: string,
    direccion?: string,
    telefono?: string,
    email?: string,
    sexo?: string,
    fechaNacimiento?: Date,
    fechaCobertura?: Date,
    saldo?: number,
    zona?: Zona,
    socio?: Socio,
    localidad?: Localidad,
    obraSocial?: ObraSocial,
    estado?: Estado,
    usuarioModifica?: Usuario,
    edad?: number
}