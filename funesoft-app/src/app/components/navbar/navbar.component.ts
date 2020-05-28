import { Component, OnInit } from '@angular/core';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '@app/services/auth.service'
import { ProvinciaService } from '@app/services/provincia.service';
import { Provincia } from '@app/model/provincia';
import { PageEnum } from '@app/utils/page.enum';
import { FunesoftResponseDTO } from '@app/dtos/funesoftRequest.dto';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [NgbDropdownConfig]
})
export class NavbarComponent implements OnInit {

  public sidebarOpened = false;
  loading: boolean = false;
  authForm: FormGroup;

  toggleOffcanvas() {
    this.sidebarOpened = !this.sidebarOpened;
    if (this.sidebarOpened) {
      document.querySelector('.sidebar-offcanvas').classList.add('active');
    }
    else {
      document.querySelector('.sidebar-offcanvas').classList.remove('active');
    }
  }

  constructor(config: NgbDropdownConfig,
              private authService: AuthService,
              private provinciaService: ProvinciaService,
              private formBuilder: FormBuilder,
              private router: Router) {
    config.placement = 'bottom-right';
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
    });

    if (!this.authService.check) {
      this.router.navigate(['/auth']);
    }

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

  getProvincias(){
    this.loading = true;
    let provincia: Provincia = {
      id: null,
      // nombre: 'BUENOS AIRES',
      // usuarioModifica: {
      //     id: 1,
      //     username: 'fausto',
      //     password: 'fedele',
      //     rol: {
      //         id: 1,
      //         nombre: 'ADMIN'
      //     },
          // enabled: true,
          // accountNonLocked: true,
          // credentialsNonExpired: true,
          // accountNonExpired: true,
          // authorities: [
          //     {
          //         authority: 'ADMIN'
          //     }
          // ]
      // }
    };
    this.provinciaService.getProvincias(provincia).subscribe(
      response => {
        console.log('Provincias', response.data);
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

}
