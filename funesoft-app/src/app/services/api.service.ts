import { Injectable } from '@angular/core';
import { environment } from '@env/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export abstract class ApiService {

  protected entrypoint: string = environment.api_url;

  constructor(protected http: HttpClient) { }

  protected request(method: MethodEnum, endpoint: EndpointEnum, headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'}), body?: any): Observable<any>{
    const url = this.entrypoint + endpoint;
    console.log('url', url);
     switch(method){
       case MethodEnum.GET:
        return this.http.get(url, {headers: headers});
       case MethodEnum.POST:
        return this.http.post(url, body, {headers: headers});
     }
  }

}
