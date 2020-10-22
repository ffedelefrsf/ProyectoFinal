import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Socio } from '@app/model/socio';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { Subject } from 'rxjs';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-informar-pago',
  templateUrl: './informar-pago.component.html',
  styleUrls: ['./informar-pago.component.scss']
})
export class InformarPagoComponent implements OnInit {

  currentRate: any;
  public typeaheadBasicModel: any;
  public typeaheadFocusModel: any;

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;

  socios: Socio[] = [];
  sociosName: string[] = [];

  socioSelected: Socio;

  informarPagoForm: FormGroup;

  selectedValue: any = {
    dni: 12345678,
    tarifa: {
      descripcion: "tarifa 1",
      plan: {
        descripcion: "plan 1"
      }
    }
  };
  activo: boolean = true;

  currentDate: Date;

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
  saldo: string;

  constructor(
    private socioService: SocioService,
    private router: Router,
    private formBuilder: FormBuilder,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit() {
    this.spinner.show();

    this.informarPagoForm = this.formBuilder.group({
      socio: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)])      
    });

    this.getSocios();

    // this.selectedValue = this.socios[0];
    
    this.currentDate = new Date();
    console.log(this.currentDate);

    // console.log("length: " + this.socios.length);


  }

  getSocios() {
    this.loading = true;
    let socio: Socio = {
      id: null,
    };
    this.socioService.getSocios(socio).subscribe(
      response => {
        this.socios = response.data;
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.socios = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.socios = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
        this.spinner.hide();
      }
    );
  }

  changeSocio(){
    console.log(this.selectedValue.saldo);

    this.saldo = "$ " + this.selectedValue.saldo;

    console.log(this.saldo);

    this.activo = this.selectedValue.estado.id === 2 ? false : true;

    // this.socioSelected = event;
    

    console.log(this.selectedValue.tarifa);    
    console.log(this.selectedValue.plan);  
  }

  createPago(){

  }

  simpleAlert(){
    Swal.fire('Pago informado', 'Se informó el pago correctamente!', 'success')
  }

}
