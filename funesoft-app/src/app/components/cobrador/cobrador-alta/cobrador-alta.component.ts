import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CobradorDTO } from '@app/dtos/cobrador.dto';
import { Cobrador } from '@app/model/cobrador';
import { Localidad } from '@app/model/localidad';
import { CobradorService } from '@app/services/cobrador.service';
import { LocalidadService } from '@app/services/localidad.service';
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
  cobradorInsert: CobradorDTO;
  altaCobradorForm: FormGroup;


  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private cobradorService: CobradorService,
    private localidadService: LocalidadService,
  ) {
    this.cobradorInsert = {};
  }

  ngOnInit() {

    // this.getLocalidades();
    let localidad: Localidad = {
      id: 1,
      nombre: "BOVRIL"
    };
    this.localidades.push(localidad);
    console.warn(this.localidades);

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
    });

  }

  getLocalidades() {
    this.loading = true;
    let localidad: Localidad = {
      id: null,
    };
    this.localidadService.getLocalidades(localidad).subscribe(
      res => {
        this.localidades = res.data;
        this.error = false;
      },
      err => {
        if (err.status === 401) {
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.localidades = [];
          this.error = true;
        } else {
          this.localidades = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
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
            this.error = false;
            this.loading = false;
          } else {
            this.loading = false;
            this.error = true;
            this.success = false;
            console.error(response.errores[0]);
          }
        },
        err => {
          this.loading = false;
          this.error = true;
          this.success = false;
        }
      );

    } else {
      this.error = true;
    }

  }

  updateLocalidades(event: any) {
    this.cobradorInsert.idLocalidad = this.localidades[event.target.selectedIndex].id;
  }

  onCancel() {
    console.log(this.altaCobradorForm.controls);
    // this.location.back();
  }

}
