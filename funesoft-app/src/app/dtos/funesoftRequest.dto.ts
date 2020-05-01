export interface FunesoftResponseDTO<T>{
    success: boolean,
    data: T[],
    message: string,
    date: Date,
    errores: string[]
}