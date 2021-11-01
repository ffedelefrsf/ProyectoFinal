import { Injectable } from '@angular/core';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { VentasDTO } from '@app/dtos/ventas.dto';
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

  getVenta(venta: Venta): Observable<FunesoftResponseDTO<Venta>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_VENTA, null, venta);
  }

  createVenta(venta: Venta): Observable<FunesoftResponseDTO<Venta>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_VENTA, null, venta);
  }

  deleteVenta(venta: Venta): Observable<FunesoftResponseDTO<Venta>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_VENTA, null, venta);
  }

  getVentas(venta: Venta): Observable<FunesoftResponseDTO<VentasDTO>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_VENTAS, null, venta);
  }

}
