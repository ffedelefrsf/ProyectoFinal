import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterTarifa'
})
export class FilterTarifaPipe implements PipeTransform {

  transform(value: any, arg: any): any {
    if (arg === '' || arg.lenght < 3) return value;
    const resultDatos = [];
    for (const dato of value) {
      if (
        dato.nroTarifa.toString().indexOf(arg.toString()) > -1 ||
        dato.descripcion.toLowerCase().indexOf(arg.toLowerCase()) > -1 ||
        dato.plan.descripcion.toLowerCase().indexOf(arg.toLowerCase()) > -1
      ) {
        resultDatos.push(dato);
      }
    }
    return resultDatos;
  }

}
