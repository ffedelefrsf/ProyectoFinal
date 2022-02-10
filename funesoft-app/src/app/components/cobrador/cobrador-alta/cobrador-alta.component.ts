import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2/dist/sweetalert2.js';

import { CobradorDTO } from '@app/dtos/cobrador.dto';
import { Cobrador } from '@app/model/cobrador';
import { Localidad } from '@app/model/localidad';
import { Provincia } from '@app/model/provincia';
import { CobradorService } from '@app/services/cobrador.service';
import { LocalidadService } from '@app/services/localidad.service';
import { ProvinciaService } from '@app/services/provincia.service';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-cobrador-alta',
  templateUrl: './cobrador-alta.component.html',
  styleUrls: ['./cobrador-alta.component.scss']
})
export class CobradorAltaComponent implements OnInit {

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  errorString: string;


  localidades: Localidad[] = [];
  provincias: Provincia[];
  cobradorInsert: CobradorDTO;
  altaCobradorForm: FormGroup;


  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private cobradorService: CobradorService,
    private provinciaService: ProvinciaService,
    private localidadService: LocalidadService,
  ) {
    this.cobradorInsert = {};
  }

  ngOnInit() {
    this.loading = true;
    this.altaCobradorForm = this.formBuilder.group({
      nombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      apellido: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      dni: this.formBuilder.control('', [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      direccion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control('', [Validators.required, Validators.minLength(9), Validators.maxLength(12), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control('', [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control('', [Validators.required]),
      fechaNacimiento: this.formBuilder.control('', [Validators.required]),
      localidad: this.formBuilder.control('', [Validators.required]),
      provincia: this.formBuilder.control('ENTRE RÍOS', [Validators.required])
    });

    var provincia: Provincia = {};
    this.provinciaService.getProvincias(provincia).subscribe(
      provinciasResponse => {
        this.provincias = provinciasResponse.data;
        var localidad: Localidad = {provincia: {id: 8}};
        this.localidadService.getLocalidades(localidad).subscribe(
          localidadesResponse => {
            this.localidades = localidadesResponse.data;
            this.loading = false;
          },
          errorLocalidades => {
            if (errorLocalidades.status === 401){
              this.router.navigate(['/'+PageEnum.AUTH]);
              this.loading = false;
            }else{
              console.log('ERROR', errorLocalidades);
            }
          }
        );
      },
      errorProvincias => {
        if (errorProvincias.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.loading = false;
        }else{
          console.log('ERROR', errorProvincias);
        }
      }
    );
  }

  createCobrador() {
    if (!this.altaCobradorForm.invalid) {

      var altaCobradorForm : Cobrador = this.altaCobradorForm.getRawValue();

      console.log(altaCobradorForm);

      this.cobradorInsert.nombre = altaCobradorForm.nombre;
      this.cobradorInsert.apellido = altaCobradorForm.apellido;
      this.cobradorInsert.dni = altaCobradorForm.dni;
      this.cobradorInsert.direccion = altaCobradorForm.direccion;
      this.cobradorInsert.telefono = altaCobradorForm.telefono;
      this.cobradorInsert.email = altaCobradorForm.email;
      this.cobradorInsert.sexo = altaCobradorForm.sexo;
      this.cobradorInsert.fechaNacimiento = altaCobradorForm.fechaNacimiento;

      this.cobradorService.createCobrador(this.cobradorInsert).subscribe(
        response => {
          if (response.success) {
            this.success = true;
            Swal.fire({
              icon: 'success',
              title: '¡Éxito!',
              titleText: 'El cobrador ha sido agregado con éxito.',
              showConfirmButton: false,
              timer: 1500
            });
            this.error = false;
            this.loading = false;
          } else {
            this.loading = false;
            this.error = true;
            this.success = false;
            Swal.fire(
              'Error',
              'Se produjo un error. Intente nuevamente.',
              'error'
            );
            console.error(response.errores[0]);
          }
        },
        err => {
          this.loading = false;
          this.error = true;
          Swal.fire(
            'Error',
            'Se produjo un error. Intente nuevamente.',
            'error'
          );
          this.success = false;
        }
      );

    } else {
      this.error = true;
    }

  }

  updateProvincias(event: any){
    var idProvincia = this.provincias[event.target.selectedIndex].id;
    var localidad: Localidad = {provincia: {id: idProvincia}};
    this.localidadService.getLocalidades(localidad).subscribe(
      response => {
        if (response.success){
          this.localidades = response.data;
        } else {
          this.localidades = null;
        }
      },
      err => {
        this.localidades = null;
      }
    );
  }

  updateLocalidades(event: any) {
    this.cobradorInsert.idLocalidad = this.localidades[event.target.selectedIndex].id;
  }

  onCancel() {
    console.log(this.altaCobradorForm.controls);
    // this.location.back();
  }

}
