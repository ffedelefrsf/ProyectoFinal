import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Adherente } from '@app/model/adherente';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { AdherenteAltaDTO } from '@app/dtos/adherenteAlta.dto';

@Injectable({
  providedIn: 'root'
})
export class AdherenteService extends ApiService{

  createAdherente(adherente: AdherenteAltaDTO): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_ADHERENTE, null, adherente);
  }

  editAdherente(adherente: Adherente): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.POST, EndpointEnum.EDIT_ADHERENTE, null, adherente);
  }

  getAdherentesOrdered(): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_ADHERENTES_ORDERED, null, null);
  }

}
