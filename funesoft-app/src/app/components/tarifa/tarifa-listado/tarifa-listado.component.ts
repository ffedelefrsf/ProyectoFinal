import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Tarifa } from '@app/model/tarifa';
import { TarifaService } from '@app/services/tarifa.service';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-tarifa-listado',
  templateUrl: './tarifa-listado.component.html',
  styleUrls: ['./tarifa-listado.component.scss']
})
export class TarifaListadoComponent implements OnInit {

  loading: boolean = false;
  tarifasArray: Tarifa[];
  error: boolean = false;
  public sidebarOpened = false;

  constructor(private tarifaService: TarifaService,
    private router: Router,
    private modalService: NgbModal,
    config: NgbDropdownConfig) { 
      config.placement = 'bottom-right'; 
    }

  ngOnInit() {
    this.getTarifas();
  }

  getTarifas(){
    this.loading = true;
    let tarifa: Tarifa = {
      id: null,
    };
    this.tarifaService.getTarifas(tarifa).subscribe(
      response => {
        this.tarifasArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.tarifasArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.tarifasArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  verRangos(tarifa: Tarifa){
    this.router.navigate(['/' + PageEnum.RANGOS_TARIFA], { 
      state: { tarifa: JSON.stringify(tarifa) } 
    });
  }

}