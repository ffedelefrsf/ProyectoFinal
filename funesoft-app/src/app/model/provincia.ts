export interface Provincia{
    id: number;
    nombre: string;
    usuarioModifica: Usuario;
}

interface Usuario{
    id: number;
    username: string;
    password: string;
    rol: Rol;
    // enabled: boolean;
    // accountNonLocked: boolean;
    // credentialsNonExpired: boolean;
    // accountNonExpired: boolean;
    // authorities: Autoridad[];
}

interface Rol{
    id: number;
    nombre: string;
}

// interface Autoridad{
//     authority: string;
// }