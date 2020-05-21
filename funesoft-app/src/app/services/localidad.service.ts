import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Localidad } from '@app/model/localidad';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class LocalidadService extends ApiService{

  getLocalidades(localidad: Localidad): Observable<FunesoftResponseDTO<Localidad>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_LOCALIDADES, null, localidad);
  }

  getNombresLocalidades(localidad: Localidad): Observable<FunesoftResponseDTO<String>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_NOMBRES_LOCALIDADES, null, localidad);
  }

}
