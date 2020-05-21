import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

import { Socio } from '@app/model/socio';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoComponent implements OnInit {

  loading: boolean = false;
  sociosArray: Socio[];
  error: boolean = false;
  public sidebarOpened = false;

  constructor(private socioService: SocioService,
              private router: Router,
              config: NgbDropdownConfig) 
    {config.placement = 'bottom-right'; }

  ngOnInit() {
    this.getSocios();
  }

  getSocios(){
    this.loading = true;
    let socio: Socio = {
      id: null,
    };
    this.socioService.getSocios(socio).subscribe(
      response => {
        this.sociosArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.sociosArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.sociosArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  editSocio(socio: Socio){
    this.router.navigate(['/'+PageEnum.SOCIO_MODIFICACION], { 
      state: { socio: JSON.stringify(socio) } 
    });
  }

  toggleOffcanvas() {
    this.sidebarOpened = !this.sidebarOpened;
    if (this.sidebarOpened) {
      document.querySelector('.sidebar-offcanvas').classList.add('active');
    }
    else {
      document.querySelector('.sidebar-offcanvas').classList.remove('active');
    }
  }

}
