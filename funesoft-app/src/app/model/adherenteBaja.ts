import { Usuario } from './usuario';
import { Adherente } from './adherente';
import { MotivoBaja } from './motivoBaja';

export interface AdherenteBaja{
    id?: number,
    adherente?: Adherente,
    motivoBaja?: MotivoBaja,
    usuarioModifica?: Usuario
}