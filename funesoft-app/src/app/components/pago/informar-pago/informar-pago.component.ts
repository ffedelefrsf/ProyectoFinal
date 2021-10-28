import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ComprobanteDTO } from '@app/dtos/comprobante.dto';
import { PagoDTO } from '@app/dtos/pago.dto';
import { Cobrador } from '@app/model/cobrador';
import { Comprobante } from '@app/model/comprobante';
import { Socio } from '@app/model/socio';
import { CobradorService } from '@app/services/cobrador.service';
import { ComprobanteService } from '@app/services/comprobante.service';
import { PagoService } from '@app/services/pago.service';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { NgSelectConfig } from '@ng-select/ng-select';
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

  comprobantesDTO: ComprobanteDTO[] = [];

  informarPagoForm: FormGroup;

  selectedValue: Socio;
  selectedSocio: Boolean = false;
  activo: boolean = true;

  currentDate: Date;

  @ViewChild('instance', { static: true }) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
  saldo: string;
  showTable: boolean = false;
  cobradores: Cobrador[];

  pagoDTO: PagoDTO = {};

  sociosSelect = [];
  socioSelected;

  constructor(
    private socioService: SocioService,
    private comprobanteService: ComprobanteService,
    private router: Router,
    private formBuilder: FormBuilder,
    private spinner: NgxSpinnerService,
    private cobradorService: CobradorService,
    private pagoService: PagoService,
  ) {
  }

  selectedCar: number;

  sociosDropdown = [];

  ngOnInit() {

    this.informarPagoForm = this.formBuilder.group({
      socio: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)])
    });

    this.getSocios();
    this.getAllCobrador();

    this.currentDate = new Date();
    console.log(this.currentDate);

  }

  getSocios() {
    this.spinner.show();
    let socio: Socio = {
      id: null,
    };
    this.socioService.getSocios(socio).subscribe(
      response => {
        this.socios = response.data.sort((a, b) => a.apellido.localeCompare(b.apellido));
        this.convertSocios();
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.socios = [];
          this.error = true;
        } else {
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

  convertSocios() {
    this.socios.map(element => {
      element['name'] = element.apellido + " " + element.nombre;
      return element;
    });
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
        this.comprobantesDTO = response.data.sort((a, b) => b.nroComprobante - a.nroComprobante);
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.comprobantesDTO = [];
          this.error = true;
        } else {
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

  changeSocio(event: any) {
    this.selectedValue = event;
    this.selectedSocio = true;
    this.showTable = false;
    this.saldo = "$ " + this.selectedValue.saldo;
    this.activo = this.selectedValue.estado.id === 2 ? false : true;
  }

  createPago(cbte: ComprobanteDTO) {

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
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {

        this.pagoDTO.valor = cbte.importeTotal;
        this.pagoDTO.idCobrador = 1;
        this.pagoDTO.idComprobante = cbte.id;
        this.pagoDTO.idSocio = cbte.socio.id;

        this.pagoService.informarPago(this.pagoDTO).subscribe(
          response => {
            if (response.success) {
              this.success = true;
              this.error = false;
              this.loading = false;
              cbte.pagado = true;
              this.selectedValue.saldo = (parseFloat(this.saldo.substring(2)) + cbte.importeTotal);
              this.saldo = '$ ' + this.selectedValue.saldo.toString();
              Swal.fire(
                'Informado!',
                'El pago del comprobante ' + cbte.nroComprobante + ' fue realizado con éxito.',
                'success'
              );
            } else {
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
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              text: 'Se produjo un error. Intenta de nuevo.'
            });
          }
        );
      }
    })
  }

  readComprobantes() {
    if (!this.selectedSocio) {
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
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.comprobantesDTO = [];
          this.error = true;
        } else {
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
