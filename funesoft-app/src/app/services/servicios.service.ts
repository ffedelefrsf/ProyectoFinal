import { Injectable } from '@angular/core';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { Servicio } from '@app/model/servicio';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MethodEnum } from '@app/utils/method.enum';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService extends ApiService {

  getServicios(servicio: Servicio): Observable<FunesoftResponseDTO<Servicio>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_TARIFAS, null, servicio);
  }

  createServicio(servicio: Servicio): Observable<FunesoftResponseDTO<Servicio>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_TARIFA, null, servicio);
  }

  deleteServicio(servicio: Servicio): Observable<FunesoftResponseDTO<Servicio>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_TARIFA, null, servicio);
  }

}
