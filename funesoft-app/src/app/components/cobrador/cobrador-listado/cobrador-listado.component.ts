import { Component, OnInit } from '@angular/core';
import { CobradorService } from '@app/services/cobrador.service';
import { Cobrador } from '@app/model/cobrador';
import { Router } from '@angular/router';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-cobrador-listado',
  templateUrl: './cobrador-listado.component.html',
  styleUrls: ['./cobrador-listado.component.scss']
})
export class CobradorListadoComponent implements OnInit {

  loading: boolean = false;
  error: boolean = false;
  cobradoresArray: Cobrador[];

  constructor(
    private cobradorService: CobradorService,
    private router: Router,
  ) { 
    
  }

  ngOnInit() {
    this.getCobradores();
  }

  getCobradores(){
    this.loading = true;
    let cobrador: Cobrador = {
      id: null,
    };
    this.cobradorService.getCobradores(cobrador).subscribe(
      response => {
        this.cobradoresArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.cobradoresArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.cobradoresArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  deleteCobrador(cobrador: Cobrador) {
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
        this.cobradorService.deleteCobrador(cobrador).subscribe(
          response => {
            if (response.success) {
              this.cobradoresArray.splice(this.cobradoresArray.lastIndexOf(cobrador), 1);
              Swal.fire(
                'Eliminado!',
                'El cobrador se ha eliminado.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'El cobrador no pudo ser eliminado',
                footer: 'Puede que alguna zona lo tenga asociado'
              })
            }
          }
        );
      }
    })
  }

}
