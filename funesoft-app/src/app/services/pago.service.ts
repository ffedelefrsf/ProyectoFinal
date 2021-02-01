import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { PagoDTO } from '@app/dtos/pago.dto';
import { Observable } from 'rxjs';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class PagoService extends ApiService  {

  informarPago(pago: PagoDTO): Observable<FunesoftResponseDTO<PagoDTO>>{
    return this.request(MethodEnum.POST, EndpointEnum.CREATE_PAGO, null, pago);
  }

}
