import { Injectable } from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/do';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    req = req.clone({
      withCredentials: true
    });

    return next
      .handle(req)
      .do(event => {
        if (event instanceof HttpResponse) {
          console.log('event', event);
        }
      }, (error: any) => {
        if (error instanceof HttpErrorResponse) {
          console.log('error', error);
          if (error.status === 401) {
            console.log('401', 'asd');
            this.router.navigate(['/auth']);
          }
        }
      });
  }
}