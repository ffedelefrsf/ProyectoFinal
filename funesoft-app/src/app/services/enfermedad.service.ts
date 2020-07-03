import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Enfermedad } from '@app/model/enfermedad';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class EnfermedadService extends ApiService{

  getEnfermedades(enfermedad: Enfermedad): Observable<FunesoftResponseDTO<Enfermedad>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_ENFERMEDADES, null, enfermedad);
  }
  
}
