import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Tarifa } from '@app/model/tarifa';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { RangoTarifa } from '@app/model/rangoTarifa';

@Injectable({
  providedIn: 'root'
})
export class RangoTarifaService extends ApiService{
  
  getRangosTarifa(tarifa: Tarifa): Observable<FunesoftResponseDTO<RangoTarifa>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_RANGOS_TARIFA, null, tarifa);
  }

}
