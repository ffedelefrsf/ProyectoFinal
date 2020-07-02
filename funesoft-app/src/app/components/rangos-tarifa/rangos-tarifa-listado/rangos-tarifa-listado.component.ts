import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Location, formatDate } from '@angular/common';

import { Tarifa } from '@app/model/tarifa';
import { RangoTarifa } from '@app/model/rangoTarifa';
import { RangoTarifaService } from '@app/services/rango-tarifa.service';

import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-rangos-tarifa-listado',
  templateUrl: './rangos-tarifa-listado.component.html',
  styleUrls: ['./rangos-tarifa-listado.component.scss']
})
export class RangosTarifaListadoComponent implements OnInit {

  loading: boolean = false;
  error: boolean = false;
  tarifa: Tarifa;
  rangoArray: RangoTarifa[];

  constructor(private router: Router,
    private location: Location,
    private rangoService: RangoTarifaService,) { 
    this.tarifa = JSON.parse(this.router.getCurrentNavigation().extras.state.tarifa);
  }

  ngOnInit() {
    this.getRangos(this.tarifa);
  }

  getRangos(tarifa: Tarifa){
    this.loading = true;
    let rango: RangoTarifa = {
      id: null,
    };
    this.rangoService.getRangosTarifa(tarifa).subscribe(
      response => {
        this.rangoArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.rangoArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.rangoArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  modificarRango(rango: RangoTarifa){
    
  }

}
