import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Cobrador } from '@app/model/cobrador';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { CobradorDTO } from '@app/dtos/cobrador.dto';

@Injectable({
  providedIn: 'root'
})
export class CobradorService extends ApiService {

  getCobradores(cobrador: Cobrador): Observable<FunesoftResponseDTO<Cobrador>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_COBRADORES, null, cobrador);
  }

  createCobrador(cobradorDTO: CobradorDTO): Observable<FunesoftResponseDTO<Cobrador>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_COBRADOR, null, cobradorDTO);
  }

  deleteCobrador(cobrador: Cobrador): Observable<FunesoftResponseDTO<Cobrador>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_COBRADOR, null, cobrador);
  }
  
}
