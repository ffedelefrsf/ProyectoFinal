import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Socio } from '@app/model/socio';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { MotivoBaja } from '@app/model/motivoBaja';
import { SocioBajaDTO } from '@app/dtos/socioBaja.dto';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SocioService extends ApiService{

  getSocios(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_SOCIOS, null, socio);
  }

  getDNIs(): Observable<FunesoftResponseDTO<string>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_DNI_SOCIOS, null, null);
  }

  editSocio(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.EDIT_SOCIO, null, socio);
  }

  createSocio(socio: Socio): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_SOCIO, null, socio);
  }

  deleteSocio(socioBajaDTO: SocioBajaDTO): Observable<FunesoftResponseDTO<Socio>>{
    return this.request(MethodEnum.POST, EndpointEnum.DELETE_SOCIO, null, socioBajaDTO);
  }

  getMotivosBaja(): Observable<FunesoftResponseDTO<MotivoBaja>>{
    return this.request(MethodEnum.GET, EndpointEnum.GET_MOTIVOS_BAJA, null, null);
  }

  getMotivoBaja(socio: Socio): Observable<FunesoftResponseDTO<MotivoBaja>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_MOTIVO_BAJA, null, socio);
  }

  getPDF(): Observable<Blob>
  {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json'});

      return this.http.get<Blob>('http://localhost:8080/Funesoft/socio/generarPDF', { headers : headers,responseType : 
      'blob' as 'json'});
  }

  getXLS(): Observable<Blob>
  {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json'});
      return this.http.get<Blob>('http://localhost:8080/Funesoft/socio/generarXLS', { headers : headers,responseType : 
      'blob' as 'json'});
  }

}
