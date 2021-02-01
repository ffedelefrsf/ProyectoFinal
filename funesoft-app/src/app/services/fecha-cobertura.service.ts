import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { FechaCoberturaGetRequestDTO } from '@app/dtos/fechaCoberturaGetRequest.dto';
import { FechaCoberturaGetResponseDTO } from '@app/dtos/fechaCoberturaGetResponse.dto';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MethodEnum } from '@app/utils/method.enum';

@Injectable({
  providedIn: 'root'
})
export class FechaCoberturaService extends ApiService{

  getFechaCobertura(fechaCoberturaGetRequestDTO: FechaCoberturaGetRequestDTO): Observable<FunesoftResponseDTO<FechaCoberturaGetResponseDTO>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_FECHA_COBERTURA, null, fechaCoberturaGetRequestDTO);
  }

}
