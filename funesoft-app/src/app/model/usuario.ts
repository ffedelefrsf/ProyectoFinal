import { Rol } from './rol';

export interface Usuario{
    id?: number;
    username?: string;
    password?: string;
    rol?: Rol;
    enabled?: boolean;
    accountNonLocked?: boolean;
    credentialsNonExpired?: boolean;
    accountNonExpired?: boolean;
    authorities?: Autoridad[];
}

interface Autoridad{
    authority?: string;
}