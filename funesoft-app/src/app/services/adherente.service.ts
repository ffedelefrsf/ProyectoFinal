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
import { GetAdherenteBySocioDNIRequestDTO } from '@app/dtos/getAdherenteBySocioDNIRequest.dto';
import { HttpHeaders } from '@angular/common/http';

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

  getAdherentesOrdered(getAdherenteBySocioDNIRequestDTO: GetAdherenteBySocioDNIRequestDTO = null): Observable<FunesoftResponseDTO<Adherente>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_ADHERENTES_ORDERED, null, getAdherenteBySocioDNIRequestDTO == null ? {} : getAdherenteBySocioDNIRequestDTO);
  }

  getMotivosBaja(): Observable<FunesoftResponseDTO<MotivoBaja>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_MOTIVOS_BAJA, null, null);
  }

  getPDF(): Observable<Blob>
  {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
      return this.http.get<Blob>('http://localhost:8080/Funesoft/adherente/generarPDF', { headers : headers});
  }

  getXLS(): Observable<Blob>
  {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
      return this.http.get<Blob>('http://localhost:8080/Funesoft/adherente/generarXLS', { headers : headers});
  }

}
