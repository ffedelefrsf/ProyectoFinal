import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Plan } from '@app/model/plan';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';
import { MethodEnum } from '@app/utils/method.enum';
import { EndpointEnum } from '@app/utils/endpoint.enum';

@Injectable({
  providedIn: 'root'
})
export class PlanService extends ApiService{

  getPlanes(plan: Plan): Observable<FunesoftResponseDTO<Plan>>{
    return this.request(MethodEnum.POST, EndpointEnum.GET_PLANES, null, plan);
  }

}
