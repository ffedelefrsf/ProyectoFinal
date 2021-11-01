import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VentasDTO } from '@app/dtos/ventas.dto';
import { Venta } from '@app/model/venta';
import { VentaService } from '@app/services/venta.service';
import { PageEnum } from '@app/utils/page.enum';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-venta-listado',
  templateUrl: './venta-listado.component.html',
  styleUrls: ['./venta-listado.component.scss']
})
export class VentaListadoComponent implements OnInit {

  loading: boolean = false;
  error: boolean = false;
  ventasArray: VentasDTO[];

  constructor(
    private ventaService: VentaService,
    private router: Router,
  ) { }

  ngOnInit() {
    this.getVentas();
  }

  getVentas(){
    this.loading = true;
    let venta: Venta = {
      id: null,
    };
    this.ventaService.getVentas(venta).subscribe(
      response => {
        debugger
        this.ventasArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.ventasArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.ventasArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  deleteVenta(ventasDTO: VentasDTO) {
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
        let venta: Venta = {
          id: ventasDTO.id,
        };
        this.ventaService.deleteVenta(venta).subscribe(
          response => {
            if (response.success) {
              this.ventasArray.splice(this.ventasArray.lastIndexOf(ventasDTO), 1);
              Swal.fire(
                'Eliminado!',
                'La venta ha sido eliminada.',
                'success'
              )
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'La venta no pudo ser eliminada',
                footer: 'Puede que alguna relación actualmente'
              })
            }
          }
        );

      }
    })
  }

}
