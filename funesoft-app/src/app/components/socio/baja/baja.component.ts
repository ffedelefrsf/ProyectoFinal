import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Location } from '@angular/common';

import { Socio } from '@app/model/socio';
import { MotivoBaja } from '@app/model/motivoBaja';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';
import { SocioBajaDTO } from '@app/dtos/socioBaja.dto';

@Component({
  selector: 'app-baja',
  templateUrl: './baja.component.html',
  styleUrls: ['./baja.component.scss']
})
export class BajaSocioComponent implements OnInit {

  @Input() socio: Socio;

  @Output() passEntry: EventEmitter<any> = new EventEmitter();

  loading: boolean = false;
  deleteSocioForm: FormGroup;
  motivosBaja: MotivoBaja[];

  constructor(private router: Router,
              private formBuilder: FormBuilder,
              public activeModal: NgbActiveModal,
              private socioService: SocioService,
              private location: Location) { }

  ngOnInit() {
    this.loading = true;

    this.deleteSocioForm = this.formBuilder.group({
      motivoBaja: this.formBuilder.control('', [Validators.required])
    });

    this.socioService.getMotivosBaja().subscribe(
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
    var socioBajaDTO: SocioBajaDTO = {
      idSocio: this.socio.id,
      idMotivoBaja: this.deleteSocioForm.controls['motivoBaja'].value
    };
    this.socioService.deleteSocio(socioBajaDTO).subscribe(
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
