import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Cobrador } from '@app/model/cobrador';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class CobradorService extends ApiService{

  getCobradores(cobrador: Cobrador): Observable<FunesoftResponseDTO<Cobrador>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_COBRADORES, null, cobrador);
  }
  
}
