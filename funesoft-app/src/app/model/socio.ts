import { Localidad } from './localidad';
import { ObraSocial } from './obraSocial';
import { Usuario } from './usuario';
import { Estado } from './estado';
import { Tarifa } from './tarifa';
import { Zona } from './zona';
import { Enfermedad } from './enfermedad';

export interface Socio{
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
    tarifa?: Tarifa,
    zona?: Zona,
    localidad?: Localidad,
    obraSocial?: ObraSocial,
    usuarioModifica?: Usuario,
    estado?: Estado,
    enfermedad?: Enfermedad,
    edad?: number,
}