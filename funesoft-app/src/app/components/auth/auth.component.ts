import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie';

import { AuthService } from '@app/services/auth.service';
import { ProvinciaService } from '@app/services/provincia.service';
import { Provincia } from '@app/model/provincia';


@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  authForm: FormGroup;
  loading : boolean = false;
  wrongCredentials : boolean = false;

  constructor( 
    private formBuilder: FormBuilder,
    private router: Router,
    private cookieService:CookieService,
    private provnciaService: ProvinciaService,
    private authService: AuthService) { 
   }

  ngOnInit(): void {
    this.authForm = this.formBuilder.group({
      username: this.formBuilder.control('', [Validators.required]),
      password: this.formBuilder.control('', [Validators.required])
    });
  }

  auth(){
    const loginDTO = this.authForm.getRawValue();
    this.loading = true;
    this.wrongCredentials = false;
    this.authService.auth(loginDTO).subscribe(
      response => {
        this.loading = false;
        console.log('response', response);
      },
      err => {
        this.loading = false;
        this.wrongCredentials = true;
      },
      () => console.log('Finally')
    );
  }

  getProvincias(){
    this.loading = true;
    let provincia: Provincia = {
      id: 1,
      nombre: 'BUENOS AIRES',
      usuarioModifica: {
          id: 1,
          username: 'fausto',
          password: 'fedele',
          rol: {
              id: 1,
              nombre: 'ADMIN'
          },
          // enabled: true,
          // accountNonLocked: true,
          // credentialsNonExpired: true,
          // accountNonExpired: true,
          // authorities: [
          //     {
          //         authority: 'ADMIN'
          //     }
          // ]
      }
    };
    this.provnciaService.getProvincias(provincia).subscribe(
      response => {
        this.loading = false;
        console.log('response', response);
      },
      err => {
        this.loading = false;
      },
      () => console.log('Finally')
    );
  }

  logout(){
    this.cookieService.remove('JSESSIONID');
    // this.router.navigate(['/login']);
  }

}
