import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

import { Provincia } from '@app/model/provincia';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MethodEnum } from '@app/utils/method.enum';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';

@Injectable({
  providedIn: 'root'
})
export class ProvinciaService extends ApiService{

  getProvincias(provincia: Provincia): Observable<FunesoftResponseDTO<Provincia>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_PROVINCIAS, null, provincia);
  }

}
