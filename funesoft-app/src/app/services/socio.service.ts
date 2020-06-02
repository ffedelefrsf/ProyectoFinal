import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Socio } from '@app/model/socio';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MotivoBaja } from '@app/model/motivoBaja';
import { SocioBajaDTO } from '@app/dtos/socioBaja.dto';

@Injectable({
  providedIn: 'root'
})
export class SocioService extends ApiService{

  getSocios(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_SOCIOS, null, socio);
  }

  editSocio(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.EDIT_SOCIO, null, socio);
  }

  createSocio(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_SOCIO, null, socio);
  }

  deleteSocio(socioBajaDTO: SocioBajaDTO): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_SOCIO, null, socioBajaDTO);
  }

  getMotivosBaja(): Observable<FunesoftResponseDTO<MotivoBaja>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_MOTIVOS_BAJA, null, null);
  }

}
