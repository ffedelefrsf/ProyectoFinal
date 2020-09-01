import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Adherente } from '@app/model/adherente';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { AdherenteAltaDTO } from '@app/dtos/adherenteAlta.dto';
import { MotivoBaja } from '@app/model/motivoBaja';
import { AdherenteBajaDTO } from '@app/dtos/adherenteBaja.dto';

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

  deleteAdherente(adherenteBajaDTO: AdherenteBajaDTO): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_ADHERENTE, null, adherenteBajaDTO);
  }

  getAdherentesOrdered(): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_ADHERENTES_ORDERED, null, null);
  }

  getMotivosBaja(): Observable<FunesoftResponseDTO<MotivoBaja>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_MOTIVOS_BAJA, null, null);
  }

}
