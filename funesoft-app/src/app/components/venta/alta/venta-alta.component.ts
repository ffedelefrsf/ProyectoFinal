import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Servicio } from '@app/model/servicio';
import { Venta } from '@app/model/venta';
import { ServiciosService } from '@app/services/servicios.service';
import { VentaService } from '@app/services/venta.service';
import { PageEnum } from '@app/utils/page.enum';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-venta-alta',
  templateUrl: './venta-alta.component.html',
  styleUrls: ['./venta-alta.component.scss']
})
export class VentaAltaComponent implements OnInit {

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;

  servicios: Servicio[] = [];
  servicioSelected: any;

  altaVentaForm: FormGroup;

  ventaInsert: Venta; 

  constructor(
    private spinner: NgxSpinnerService,
    private serviciosService: ServiciosService,
    private router: Router,
    private formBuilder: FormBuilder,
    private ventaService: VentaService,
  ) {
    this.ventaInsert = {};
   }

  ngOnInit() {
    this.getServicios();
    this.altaVentaForm = this.formBuilder.group({
      nroVenta: this.formBuilder.control('0', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('^[0-9]*')]),
      descripcion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)])
    });
  }

  getServicios() {
    this.spinner.show();
    let servicio: Servicio = {
      id: null,
    };
    this.serviciosService.getServicios(servicio).subscribe(
      response => {
        this.servicios = response.data;
        this.convertServicios();
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.servicios = [];
          this.error = true;
        } else {
          console.log('ERROR', error);
          this.servicios = [];
          this.error = true;
        }
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  convertServicios() {
    this.servicios.map(element => {
      element['name'] = element.descripcion;
      return element;
    });
  }

  changeServicio(event: any) {
    this.servicioSelected = event;
  }

  createVenta() {
    if (!this.altaVentaForm.invalid) {
      var altaVentaForm: Venta = this.altaVentaForm.getRawValue();
      this.ventaInsert.nroVenta = altaVentaForm.nroVenta;
      this.ventaInsert.descripcion = altaVentaForm.descripcion;

      this.ventaInsert.servicio = this.servicioSelected;

      this.ventaService.createVenta(this.ventaInsert).subscribe(
        response => {
          if (response.success){
            this.success = true;
            this.error = false;
            this.loading = false;
            Swal.fire(
              '¡Creada!',
              'La venta ha sido registrada con éxito.',
              'success'
            );
          }else{
            this.loading = false;
            this.error = true;
            this.success = false;
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Se produjo un error. Intenta de nuevo.'
            });
            console.error(response.errores);
          }
        },
        err => {
          this.loading = false;
          this.error = true;
          this.success = false;
        }
      );

    }
  }

  onCancel() {
  }

}
