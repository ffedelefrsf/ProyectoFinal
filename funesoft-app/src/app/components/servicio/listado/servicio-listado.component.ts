import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Servicio } from '@app/model/servicio';
import { ServiciosService } from '@app/services/servicios.service';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-servicio-listado',
  templateUrl: './servicio-listado.component.html',
  styleUrls: ['./servicio-listado.component.scss']
})
export class ServicioListadoComponent implements OnInit {

  servicioArray: Servicio[];
  loading: boolean = false;
  error: boolean = false;

  constructor(
    private serviciosService: ServiciosService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.getServicios();
  }

  getServicios(){
    this.loading = true;
    let servicio: Servicio = {
      id: null,
    };
    this.serviciosService.getServicios(servicio).subscribe(
      response => {
        this.servicioArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.servicioArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.servicioArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  deleteServicio(servicio: Servicio) {
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

        this.serviciosService.deleteServicio(servicio).subscribe(
          response => {
            if (response.success) {
              this.servicioArray.splice(this.servicioArray.lastIndexOf(servicio), 1);
              Swal.fire(
                'Eliminado!',
                'El servicio se ha eliminado.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'El servicio no pudo ser eliminado',
                footer: 'Puede que alguna venta lo tenga asociado'
              })
            }
          }
        );

      }
    })
  }

}
