import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterData'
})
export class FilterDataPipe implements PipeTransform {

  transform(value: any, arg: any): any {
    if (arg === '' || arg.lenght < 3) return value;
    const resultDatos = [];
    for (const dato of value) {
      if (
        dato.nombre.toLowerCase().indexOf(arg.toLowerCase()) > -1 ||
        dato.apellido.toLowerCase().indexOf(arg.toLowerCase()) > -1 ||
        dato.dni.toString().indexOf(arg.toString()) > -1
      ) {
        resultDatos.push(dato);
      }
    }
    return resultDatos;
  }

}
