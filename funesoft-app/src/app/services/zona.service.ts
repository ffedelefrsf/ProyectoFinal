import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Zona } from '@app/model/zona';
import { ApiService } from './api.service';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { ZonaCobradoresDTO } from '@app/dtos/zonaCobradores.dto';

@Injectable({
  providedIn: 'root'
})
export class ZonaService extends ApiService{

  getZonas(zona: Zona): Observable<FunesoftResponseDTO<Zona>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_ZONAS, null, zona);
  }

  createZona(zonaCobradoresDTO: ZonaCobradoresDTO): Observable<FunesoftResponseDTO<Zona>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_ZONA, null, zonaCobradoresDTO);
  }

}
