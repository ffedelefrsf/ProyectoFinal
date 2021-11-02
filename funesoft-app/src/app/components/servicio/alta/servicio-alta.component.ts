import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Servicio } from '@app/model/servicio';
import { ServiciosService } from '@app/services/servicios.service';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-servicio-alta',
  templateUrl: './servicio-alta.component.html',
  styleUrls: ['./servicio-alta.component.scss']
})
export class ServicioAltaComponent implements OnInit {

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  disableField: boolean = false;

  altaServicioForm: FormGroup;
  servicioInsert: Servicio; 

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private servicioService: ServiciosService
  ) {
    this.servicioInsert = {};
   }

  ngOnInit() {
    this.altaServicioForm = this.formBuilder.group({
      nroServicio: this.formBuilder.control('0', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('^[0-9]*')]),
      descripcion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      valor: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')])
    });
  }

  createServicio() {
    if (!this.altaServicioForm.invalid) {
      var altaServicioForm: Servicio = this.altaServicioForm.getRawValue();
      this.servicioInsert.nroServicio = altaServicioForm.nroServicio;
      this.servicioInsert.descripcion = altaServicioForm.descripcion;
      this.servicioInsert.valor = altaServicioForm.valor;

      this.servicioService.createServicio(this.servicioInsert).subscribe(
        response => {
          if (response.success){
            this.success = true;
            this.error = false;
            this.loading = false;
            Swal.fire(
              '¡Creado!',
              'El servicio ha sido creado con éxito.',
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
