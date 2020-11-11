import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ComprobanteDTO } from '@app/dtos/comprobante.dto';
import { Cobrador } from '@app/model/cobrador';
import { Comprobante } from '@app/model/comprobante';
import { Socio } from '@app/model/socio';
import { CobradorService } from '@app/services/cobrador.service';
import { ComprobanteService } from '@app/services/comprobante.service';
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

  comprobantesDTO: ComprobanteDTO[] = [];

  informarPagoForm: FormGroup;

  selectedValue: Socio;
  selectedSocio: Boolean = false;
  activo: boolean = true;

  currentDate: Date;

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
  saldo: string;
  showTable: boolean = false;
  cobradores: Cobrador[];

  constructor(
    private socioService: SocioService,
    private comprobanteService: ComprobanteService,
    private router: Router,
    private formBuilder: FormBuilder,
    private spinner: NgxSpinnerService,
    private cobradorService: CobradorService,
  ) { }

  ngOnInit() {
    
    this.informarPagoForm = this.formBuilder.group({
      socio: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)])      
    });

    this.getSocios();
    // this.getAllComprobantes();
    this.getAllCobrador();
    
    this.currentDate = new Date();
    console.log(this.currentDate);

    console.log("COBRADORES: " + JSON.stringify(this.cobradores));
    
    


  }

  getSocios() {
    this.spinner.show();
    let socio: Socio = {
      id: null,
    };
    this.socioService.getSocios(socio).subscribe(
      response => {
        this.socios = response.data.sort((a,b) => a.apellido.localeCompare(b.apellido));
        // this.socios = response.data;
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401){
          console.log('ERROR', error);
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
        this.spinner.hide();
      }
    );
  }

  getAllComprobantes(socio: Socio) {
    this.spinner.show();
    let comprobante: Comprobante = {
      socio: {
        id: socio.id,
      },
    };
    this.comprobanteService.getAll(comprobante).subscribe(
      response => {
        this.comprobantesDTO = response.data;
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401){
          console.log('ERROR', error);
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.comprobantesDTO = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.comprobantesDTO = [];
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
    this.selectedSocio = true;
    this.showTable = false;
    console.log("SOCIO: " + this.selectedValue);
    this.saldo = "$ " + this.selectedValue.saldo;
    this.activo = this.selectedValue.estado.id === 2 ? false : true;
  }

  createPago(cbte: ComprobanteDTO){

    const cobradoresSelect = [];
    this.cobradores.forEach((item) => {
      cobradoresSelect.push(
        {
          nameV: item.nombre + ' ' + item.apellido
        },
      );
      console.log(cobradoresSelect);      
    });

    Swal.fire({
      title: 'Seleccione el cobrador',
      input: 'select',
      inputOptions: cobradoresSelect,
      showCancelButton: true,
      confirmButtonText: 'Informar',
      showLoaderOnConfirm: true,
      preConfirm: (ob, asd) => {
        debugger
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      debugger
      if (result.isConfirmed) {
        Swal.fire('Pago informado', 'El pago del comprobante Nº ' + cbte.nroComprobante + ' se registró correctamente!', 'success');
      }
    })

    // Swal.fire({
    //   title: 'Seleccione el cobrador',
    //   input: 'select',
    //   inputOptions: this.cobradores
    // });

    // console.log("CBTE: " + JSON.stringify(cbte));
    // Swal.fire('Pago informado', 'El pago del comprobante Nº ' + cbte.nroComprobante + ' se registró correctamente!', 'success')
  }

  readComprobantes() {
    if(!this.selectedSocio) {
      Swal.fire('Elija un socio', 'Seleccione un socio para recuperar sus comprobantes', 'error')
    } else {
      this.showTable = true;
      this.getAllComprobantes(this.selectedValue);
      console.log("DTOS: " + this.comprobantesDTO);
    }
  }

  getAllCobrador() {
    this.spinner.show();
    let cobrador: Cobrador = {
    };
    this.cobradorService.getCobradores(cobrador).subscribe(
      response => {
        this.cobradores = response.data;
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401){
          console.log('ERROR', error);
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.comprobantesDTO = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.comprobantesDTO = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
        this.spinner.hide();
      }
    );
  }

}
