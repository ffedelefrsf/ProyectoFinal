import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Socio } from '@app/model/socio';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class SocioService extends ApiService{

  getSocios(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_SOCIOS, null, socio);
  }

}
