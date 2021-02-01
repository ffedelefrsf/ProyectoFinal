import { Usuario } from './usuario';
import { ParametroEmpresa } from './parametroEmpresa';
import { Socio } from './socio';

export interface Comprobante{
    id?: number,
    nroComprobante?: number,
    importeTotal?: number,
    socio?: Socio,
    impreso?: number,
    usuarioModifica?: Usuario
}