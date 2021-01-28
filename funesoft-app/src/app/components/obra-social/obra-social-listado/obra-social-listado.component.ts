import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ObraSocial } from '@app/model/obraSocial';
import { ObraSocialService } from '@app/services/obra-social.service';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-obra-social-listado',
  templateUrl: './obra-social-listado.component.html',
  styleUrls: ['./obra-social-listado.component.scss']
})
export class ObraSocialListadoComponent implements OnInit {

  loading: boolean = false;
  osArray: ObraSocial[];
  error: boolean = false;
  success: boolean = false;

  constructor(
    private obraSocialService: ObraSocialService,
    private router: Router
  ) { }

  ngOnInit() {
    this.getObrasSociales();
  }

  getObrasSociales() {
    this.loading = true;
    let os: ObraSocial = {
      id: null,
    };
    this.obraSocialService.getObrasSociales(os).subscribe(
      response => {
        this.osArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401) {
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.osArray = [];
          this.error = true;
        } else {
          console.log('ERROR', error);
          this.osArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  deleteOs(os: ObraSocial) {

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

        this.obraSocialService.deleteObraSocial(os).subscribe(
          response => {
            if (response.success) {
              this.osArray.splice(this.osArray.lastIndexOf(os), 1);
              Swal.fire(
                'Eliminada!',
                'La obra social se ha eliminado.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La obra social no pudo ser eliminada',
                footer: 'Puede que algún cliente la tenga asociada'
              })
            }
          }
        );

      }
    })



    // Swal.fire({
    //   title: 'Estás seguro?',
    //   text: "Esta acción no se puede revertir",
    //   icon: 'warning',
    //   showCancelButton: true,
    //   confirmButtonColor: '#3085d6',
    //   cancelButtonColor: '#d33',
    //   confirmButtonText: 'Si, estoy seguro!'
    // }).then((result) => {
    //   debugger
    //   if (result.isConfirmed) {



    //   }
    // })

  }

}
