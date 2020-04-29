import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { AuthService } from '@app/services/auth.service';
import { PageEnum } from '@app/utils/page.enum';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
    constructor(
        private router: Router,
        private authService: AuthService
    ) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean|Observable<boolean> {
        return this.authService.check().map(
            response => {
                if (response){
                    return true;
                }else{
                    this.router.navigate(['/'+PageEnum.AUTH]);
                    return false;
                }
              }
        );
    }
}