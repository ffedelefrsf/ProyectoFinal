import { Zona } from '@app/model/zona';
import { Cobrador } from '@app/model/cobrador';

export interface ZonaCobradoresDTO{
    zona?: Zona;
    cobradores?: Cobrador[];
}