import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Comprobante } from '@app/model/comprobante';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ComprobanteService extends ApiService{

  generarComprobantesMasivos(): Observable<FunesoftResponseDTO<Comprobante>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_COMPROBANTES, null, null);
  }

  getPDF(): Observable<Blob>
  {
      const headers = new HttpHeaders({ 'Content-Type': 'application/json'});

      return this.http.get<Blob>('http://localhost:8080/Funesoft/comprobante/generarPDF', { headers : headers,responseType : 
      'blob' as 'json'});
  }
}
