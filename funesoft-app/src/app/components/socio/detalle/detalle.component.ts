import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Socio } from '@app/model/socio';
import { SocioService } from '@app/services/socio.service';
import { Router } from '@angular/router';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.scss']
})
export class DetalleSocioComponent implements OnInit {

  @Input() socio: Socio;

  loading: boolean = false;
  detailSocioForm: FormGroup;
  isBaja: boolean;
  motivoBaja: string;

  error: boolean = false;

  constructor(private formBuilder: FormBuilder,
              public activeModal: NgbActiveModal,
              private socioService: SocioService,
              private router: Router) { 
   }

  ngOnInit() {
    this.loading = true;
    this.getMotivoBaja();
    this.detailSocioForm = this.formBuilder.group({
      nombre: this.formBuilder.control(this.socio.nombre, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control(this.socio.apellido, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control(this.socio.dni, [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control(this.socio.email, [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control(this.socio.sexo, [Validators.required]),
      fechaNacimiento: this.formBuilder.control(this.socio.fechaNacimiento, [Validators.required]),
      direccion: this.formBuilder.control(this.socio.direccion, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control(this.socio.telefono, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control(this.socio.zona.nombre, [Validators.required]),
      localidad: this.formBuilder.control(this.socio.localidad.nombre, [Validators.required]),
      provincia: this.formBuilder.control(this.socio.localidad.provincia.nombre, [Validators.required]),
      obraSocial: this.formBuilder.control(this.socio.obraSocial.descripcion, [Validators.required]),
      fechaCobertura: this.formBuilder.control(this.socio.fechaCobertura, [Validators.required]),
      tarifa: this.formBuilder.control(this.socio.tarifa.descripcion, [Validators.required]),
      plan: this.formBuilder.control(this.socio.tarifa.plan.descripcion, [Validators.required]),
      enfermedad: this.formBuilder.control(this.socio.enfermedad.descripcion, [Validators.required]),
      estado: this.formBuilder.control(this.socio.estado, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      motivo_baja: this.formBuilder.control(this.socio.direccion, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
    });

    var fechaNacimiento: Date = new Date(this.socio.fechaNacimiento);
    var fechaCobertura: Date = new Date(this.socio.fechaCobertura);
    var fechaNacimientoForDatePicker = {year: fechaNacimiento.getFullYear(), month: fechaNacimiento.getMonth()+1, day: fechaNacimiento.getDate()+1};
    var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
    this.detailSocioForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);
    this.detailSocioForm.get('fechaNacimiento').setValue(fechaNacimientoForDatePicker);

    this.loading = false;
  }

  getMotivoBaja() {
    this.socioService.getMotivoBaja(this.socio).subscribe(
      response => {
        if(response.data) {
          this.isBaja = true;
          this.motivoBaja = response.data['descripcion'];
        } else {
          this.isBaja = false;
        }
        
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

}
