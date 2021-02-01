import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Tarifa } from '@app/model/tarifa';
import { TarifaService } from '@app/services/tarifa.service';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

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

  deleteTarifa(tarifa: Tarifa) {

    Swal.fire({
      title: 'Estás seguro?',
      text: "Esta acción no se puede revertir",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, estoy seguro!'
    }).then((result) => {
      if (result.isConfirmed) {

        this.tarifaService.deleteTarifa(tarifa).subscribe(
          response => {
            if (response.success) {
              this.tarifasArray.splice(this.tarifasArray.lastIndexOf(tarifa), 1);
              Swal.fire(
                'Eliminada!',
                'La tarifa se ha eliminado.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La tarifa no pudo ser eliminada',
                footer: 'Puede que algún cliente la tenga asociada'
              })
            }
          }
        );

      }
    })

  }

}
