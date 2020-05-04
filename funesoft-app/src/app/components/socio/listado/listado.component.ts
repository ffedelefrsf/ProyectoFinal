import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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

  constructor(private socioService: SocioService,
              private router: Router) 
    { }

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
        console.log('Socios', response.data);
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

  sendSocio(socio: Socio){
    this.router.navigate(['/'+PageEnum.SOCIO_MODIFICACION], { 
      state: { socio: JSON.stringify(socio) } 
    });
  }

}