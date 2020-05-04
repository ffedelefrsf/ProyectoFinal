import { MotivoBaja } from './motivoBaja';
import { Usuario } from './usuario';
import { Socio } from './socio';

export interface SocioBaja{
    id?: number,
    socio?: Socio,
    motivoBaja?: MotivoBaja,
    usuarioModifica?: Usuario
}