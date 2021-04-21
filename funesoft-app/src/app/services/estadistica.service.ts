import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Cobrador } from '@app/model/cobrador';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { CobradorDTO } from '@app/dtos/cobrador.dto';
import { EstadisticaDTO } from '@app/dtos/estadistica.dto';
import { DeudorDTO } from '@app/dtos/deudor.dto';
import { HistoricoDTO } from '@app/dtos/historico.dto';

@Injectable({
  providedIn: 'root'
})
export class EstadisticaService extends ApiService {

  getEstadisticas(): Observable<FunesoftResponseDTO<EstadisticaDTO>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_ESTADISTICAS, null);
  }

  getDeudores(): Observable<FunesoftResponseDTO<DeudorDTO>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_DEUDORES, null);
  }

  getHistorico(): Observable<FunesoftResponseDTO<HistoricoDTO>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_HISTORICO, null);
  }
  
}
