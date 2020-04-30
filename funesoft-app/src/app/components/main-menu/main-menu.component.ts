import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '@app/services/auth.service'
import { ProvinciaService } from '@app/services/provincia.service';
import { Provincia } from '@app/model/provincia';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.scss']
})
export class MainMenuComponent implements OnInit {

  loading: boolean = false;
  authForm: FormGroup;

  constructor(private authService: AuthService,
              private provinciaService: ProvinciaService,
              private formBuilder: FormBuilder,
              private router: Router) { }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
    });
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
    this.provinciaService.getProvincias(provincia).subscribe(
      response => {
        console.log('Provincias', response);
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
        }else{
          console.log('ERROR', error);
        }
      },
      () => this.loading = false
    );
  }

  logout(){
    this.loading = true;
    this.authService.logout().subscribe(
      response => {
        if (response){
          console.log('response', 'DESLOGEADO');
        }else{
          console.log('response', 'ERROR');
        }
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
        }else{
          console.log('ERROR', error);
        }
      },
      () => this.loading = false // finally
    );
  }

}
