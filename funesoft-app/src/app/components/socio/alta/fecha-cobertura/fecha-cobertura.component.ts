import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { Socio } from '@app/model/socio';
import { FechaCoberturaDTO } from '@app/dtos/fechaCobertura.dto';
import { SocioService } from '@app/services/socio.service';

@Component({
  selector: 'app-fecha-cobertura',
  templateUrl: './fecha-cobertura.component.html',
  styleUrls: ['./fecha-cobertura.component.scss']
})
export class FechaCoberturaComponent implements OnInit {

  @Input() socio: Socio;

  @Output() passEntry: EventEmitter<any> = new EventEmitter();

  loading: boolean = false;
  error: boolean = false;
  fechaCoberturaActual: Date;
  fechaCoberturaForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              public activeModal: NgbActiveModal,
              private socioService: SocioService) { 
  }

  ngOnInit() {
    this.loading = true;

    this.fechaCoberturaForm = this.formBuilder.group({
      fechaCobertura: this.formBuilder.control(this.socio.fechaCobertura, [Validators.required])
    });
    this.fechaCoberturaActual = new Date(this.socio.fechaCobertura);
    var fechaCoberturaForDatePicker = {year: this.fechaCoberturaActual.getFullYear(), month: this.fechaCoberturaActual.getMonth()+1, day: this.fechaCoberturaActual.getDate()};
    this.fechaCoberturaForm.get('fechaCobertura').setValue(fechaCoberturaForDatePicker);

    this.loading = false;
  }

  updateFechaCobertura(){
    if (!this.fechaCoberturaForm.invalid){
      var fechaCoberturaInsertada: FechaCoberturaDTO = this.fechaCoberturaForm.getRawValue();
      var fechaCoberturaInsertadaDate: Date = new Date(fechaCoberturaInsertada.fechaCobertura['year'] + '-' + fechaCoberturaInsertada.fechaCobertura['month'] + '-' + fechaCoberturaInsertada.fechaCobertura['day']);
      if (this.fechaCoberturaActual.getTime() != fechaCoberturaInsertadaDate.getTime()){
        this.socio.fechaCobertura = fechaCoberturaInsertadaDate;
        this.loading = true;
        this.socioService.editSocio(this.socio).subscribe(
          response => {
            if (response.success){
              this.error = false;
              this.loading = false;
              this.passEntry.emit(true);
              this.activeModal.close('Ok click');
            }else{
              this.loading = false;
              this.error = true;
            }
          },
          err => {
            this.loading = false;
            this.error = true;
          }
        );
      }else{
        this.error = false;
        this.loading = false;
        this.passEntry.emit(true);
        this.activeModal.close('Ok click');
      }
    }
  }

}
