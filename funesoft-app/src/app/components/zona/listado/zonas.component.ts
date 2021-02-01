import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Zona } from '@app/model/zona';
import { ZonaService } from '@app/services/zona.service';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-zonas-listado',
  templateUrl: './zonas.component.html',
  styleUrls: ['./zonas.component.scss']
})
export class ZonasComponent implements OnInit {

  loading: boolean = false;
  zonasArray: Zona[];
  error: boolean = false;
  public sidebarOpened = false;

  constructor(private zonaService: ZonaService,
    private router: Router,
    private modalService: NgbModal,
    config: NgbDropdownConfig) 
{config.placement = 'bottom-right'; }

  ngOnInit() {
    this.getZonas();
  }

  getZonas(){
    this.loading = true;
    let zona: Zona = {
      id: null,
    };
    this.zonaService.getZonas(zona).subscribe(
      response => {
        this.zonasArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.zonasArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.zonasArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
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

  

  deleteZona(zona: Zona) {
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

        this.zonaService.deleteZona(zona).subscribe(
          response => {
            if (response.success) {
              this.zonasArray.splice(this.zonasArray.lastIndexOf(zona), 1);
              Swal.fire(
                'Eliminada!',
                'La zona se ha eliminado.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La zona no pudo ser eliminada',
                footer: 'Puede que algún cliente la tenga asociada'
              })
            }
          }
        );

      }
    })
  }

}
