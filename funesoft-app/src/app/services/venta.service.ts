import { Injectable } from '@angular/core';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { Tarifa } from '@app/model/tarifa';
import { Venta } from '@app/model/venta';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MethodEnum } from '@app/utils/method.enum';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class VentaService extends ApiService {

  getVenta(venta: Venta): Observable<FunesoftResponseDTO<Tarifa>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_TARIFAS, null, venta);
  }

  createVenta(venta: Venta): Observable<FunesoftResponseDTO<Venta>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_TARIFA, null, venta);
  }

  deleteTarifa(venta: Venta): Observable<FunesoftResponseDTO<Venta>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_TARIFA, null, venta);
  }

}
