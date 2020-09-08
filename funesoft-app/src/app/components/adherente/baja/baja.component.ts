import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

import { Adherente } from '@app/model/adherente';
import { MotivoBaja } from '@app/model/motivoBaja';
import { AdherenteService } from '@app/services/adherente.service';
import { PageEnum } from '@app/utils/page.enum';
import { AdherenteBajaDTO } from '@app/dtos/adherenteBaja.dto';

@Component({
  selector: 'app-baja',
  templateUrl: './baja.component.html',
  styleUrls: ['./baja.component.scss']
})
export class BajaAdherenteComponent implements OnInit {

  @Input() adherente: Adherente;

  @Output() passEntry: EventEmitter<any> = new EventEmitter();

  loading: boolean = false;
  deleteAdherenteForm: FormGroup;
  motivosBaja: MotivoBaja[];

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              public activeModal: NgbActiveModal,
              private adherenteService: AdherenteService,
              private location: Location) { }

  ngOnInit() {
    this.loading = true;

    this.deleteAdherenteForm = this.formBuilder.group({
      motivoBaja: this.formBuilder.control('', [Validators.required])
    });

    this.adherenteService.getMotivosBaja().subscribe(
      motivosBajaResponse => {
        this.motivosBaja = motivosBajaResponse.data;
        console.log('response', this.motivosBaja);
        this.loading = false;
      },
      errorMotivosBaja => {
        if (errorMotivosBaja.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.loading = false;
        }else{
          console.log('ERROR', errorMotivosBaja);
        }
      }
    );
  }

  baja(){
    this.loading = true;
    var adherenteBajaDTO: AdherenteBajaDTO = {
      idAdherente: this.adherente.id,
      idMotivoBaja: this.deleteAdherenteForm.controls['motivoBaja'].value
    };
    this.adherenteService.deleteAdherente(adherenteBajaDTO).subscribe(
      deleteResponse => {
        this.loading = false;
        this.successfullyDeleted();
      },
      errorDelete => {
        if (errorDelete.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.loading = false;
        }else{
          console.log('ERROR', errorDelete);
        }
      },
      () => {
        this.activeModal.close('Ok click');
      }
    );
  }

  successfullyDeleted(){
    this.passEntry.emit(true);
  }

}