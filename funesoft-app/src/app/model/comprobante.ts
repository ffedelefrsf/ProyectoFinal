import { Usuario } from './usuario';
import { ParametroEmpresa } from './parametroEmpresa';

export interface Comprobante{
    id?: number,
    nroComprobante?: number,
    importeTotal?: number,
    parametroEmpresa?: ParametroEmpresa,
    usuarioModifica?: Usuario
}