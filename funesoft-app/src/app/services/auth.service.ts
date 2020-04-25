import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { environment } from '@env/environment';
import { LoginDTO } from '@app/dtos/loginDTO';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { MethodEnum } from '@app/utils/methodEnum';
import { EndpointEnum } from '@app/utils/endpointEnum';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends ApiService{

  auth(loginDTO: LoginDTO): Observable<any>{
    let headers = new HttpHeaders({'Authorization': 'Basic ' + btoa(loginDTO.username+":"+loginDTO.password)});
    return this.request(MethodEnum.POST, EndpointEnum.LOGIN, headers, loginDTO);
  }

}
