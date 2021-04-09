import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Cobrador } from '@app/model/cobrador';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { CobradorDTO } from '@app/dtos/cobrador.dto';
import { EstadisticaDTO } from '@app/dtos/estadistica.dto';

@Injectable({
  providedIn: 'root'
})
export class EstadisticaService extends ApiService {

  getEstadisticas(): Observable<FunesoftResponseDTO<EstadisticaDTO>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_ESTADISTICAS, null);
  }
  
}
