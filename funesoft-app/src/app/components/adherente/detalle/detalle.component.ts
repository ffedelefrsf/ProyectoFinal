import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Adherente } from '@app/model/adherente';

@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.scss']
})
export class DetalleAdherenteComponent implements OnInit {

  @Input() adherente: Adherente;

  loading: boolean = false;
  detailAdherenteForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              public activeModal: NgbActiveModal) { 
   }

  ngOnInit() {
    this.loading = true;

    this.detailAdherenteForm = this.formBuilder.group({
      nombre: this.formBuilder.control({value: this.adherente.nombre, disabled: true}, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      apellido: this.formBuilder.control({value: this.adherente.apellido, disabled: true}, [Validators.required, Validators.maxLength(100), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      dni: this.formBuilder.control({value: this.adherente.dni, disabled: true}, [Validators.required, Validators.minLength(7), Validators.maxLength(8), Validators.pattern('^[0-9]*')]),
      email: this.formBuilder.control({value: this.adherente.email, disabled: true}, [Validators.required, Validators.email, Validators.maxLength(100)]),
      sexo: this.formBuilder.control({value: this.adherente.sexo, disabled: true}, [Validators.required]),
      fechaNacimiento: this.formBuilder.control({value: this.adherente.fechaNacimiento, disabled: true}, [Validators.required]),
      direccion: this.formBuilder.control({value: this.adherente.direccion, disabled: true}, [Validators.required, Validators.maxLength(100), Validators.pattern('^[A-Z-Ñ a-z-ñ 0-9]*')]),
      telefono: this.formBuilder.control({value: this.adherente.telefono, disabled: true}, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern('^[0-9]*')]),
      zona: this.formBuilder.control({value: this.adherente.zona.nombre, disabled: true}, [Validators.required]),
      localidad: this.formBuilder.control({value: this.adherente.localidad.nombre, disabled: true}, [Validators.required]),
      obraSocial: this.formBuilder.control({value: this.adherente.obraSocial.descripcion, disabled: true}, [Validators.required]),
      fechaCobertura: this.formBuilder.control({value: this.adherente.fechaCobertura, disabled: true}, [Validators.required]),
      enfermedad: this.formBuilder.control({value: this.adherente.enfermedad.descripcion, disabled: true}, [Validators.required]),
      socio: this.formBuilder.control({value: this.adherente.socio.apellido + ', ' + this.adherente.socio.nombre, disabled: true}, [Validators.required])
    });

    var fechaNacimiento: Date = new Date(this.adherente.fechaNacimiento);
    var fechaCobertura: Date = new Date(this.adherente.fechaCobertura);
    var fechaNacimientoForDatePicker = {year: fechaNacimiento.getFullYear(), month: fechaNacimiento.getMonth()+1, day: fechaNacimiento.getDate()+1};
    var fechaCoberturaForDatePicker = {year: fechaCobertura.getFullYear(), month: fechaCobertura.getMonth()+1, day: fechaCobertura.getDate()+1};
    this.detailAdherenteForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);
    this.detailAdherenteForm.get('fechaNacimiento').setValue(fechaNacimientoForDatePicker);

    this.loading = false;
  }

}
