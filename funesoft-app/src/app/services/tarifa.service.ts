import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Tarifa } from '@app/model/tarifa';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class TarifaService extends ApiService{
  
  getTarifas(tarifa: Tarifa): Observable<FunesoftResponseDTO<Tarifa>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_TARIFAS, null, tarifa);
  }

}
