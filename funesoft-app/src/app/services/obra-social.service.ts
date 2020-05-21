import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ObraSocial } from '@app/model/obraSocial';
import { ApiService } from './api.service';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class ObraSocialService extends ApiService{

  getObrasSociales(obraSocial: ObraSocial): Observable<FunesoftResponseDTO<ObraSocial>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_OBRAS_SOCIALES, null, obraSocial);
  }

}
