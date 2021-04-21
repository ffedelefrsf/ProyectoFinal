import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { DeudorDTO } from '@app/dtos/deudor.dto';
import { ComprobanteDTO } from '@app/dtos/comprobante.dto';
import { EstadisticaDTO } from '@app/dtos/estadistica.dto';
import { Comprobante } from '@app/model/comprobante';
import { ComprobanteService } from '@app/services/comprobante.service';
import { EstadisticaService } from '@app/services/estadistica.service';
import { PageEnum } from '@app/utils/page.enum';
import { NgxSpinnerService } from 'ngx-spinner';
import 'moment/locale/es';
import * as moment from 'moment';
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { SocioBajaDTO } from '@app/dtos/socioBaja.dto';
import { SocioService } from '@app/services/socio.service';
import { HistoricoDTO } from '@app/dtos/historico.dto';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['../../app.component.scss', './dashboard.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class DashboardComponent implements OnInit {

  currentDate: string;
  currentDay: string;
  estadisticas: EstadisticaDTO[];
  historicos: HistoricoDTO[];
  comprobantesDTO: ComprobanteDTO[] = [];
  deudoresDTO: DeudorDTO[] = [];
  success: boolean = false;
  error: boolean = false;

  constructor(
    private estadisticaService: EstadisticaService,
    private spinner: NgxSpinnerService,
    private router: Router,
    private socioService: SocioService,
  ) { }

  ngOnInit() {
    this.setDates();
    this.getEstadisticasHeader();
    this.getAllDeudores();
    this.getHistorico();
  }

  setDates() {
    const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Deciembre"
    ];

    const date = new Date();
    this.currentDate = date.getUTCDate() + " de " + monthNames[date.getMonth()] + ", " + date.getFullYear();
    this.currentDay = date.toLocaleDateString('es-AR', { weekday: 'long' });
  }

  getEstadisticasHeader(){
    this.spinner.show();
    this.estadisticaService.getEstadisticas().subscribe(
      response => {
        this.estadisticas = response.data;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
        } else {
          console.log('ERROR', error);
        }
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  getHistorico(){
    this.spinner.show();
    this.estadisticaService.getHistorico().subscribe(
      response => {
        this.historicos = response.data;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
        } else {
          console.log('ERROR', error);
        }
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  getAllDeudores() {
    this.spinner.show();
    this.estadisticaService.getDeudores().subscribe(
      response => {
        this.deudoresDTO = response.data;
        this.error = false;
        this.spinner.hide();
      },
      error => {
        if (error.status === 401) {
          console.log('ERROR', error);
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.deudoresDTO = [];
          this.error = true;
        } else {
          console.log('ERROR', error);
          this.deudoresDTO = [];
          this.error = true;
        }
      },
      () => {
        this.spinner.hide();
      }
    );
  }

  getFechaBaja(fechaPrimerCbte: string) {  // fechaPrimerCbte: "2021-03-30"
    return moment(fechaPrimerCbte).add(3, 'months').format('DD/MM/yyyy');
  }

  darBaja(deudor: DeudorDTO){
    Swal.fire({
      title: 'Estás seguro?',
      text: 'El socio ' + deudor.apellido + ' ' + deudor.nombre + ' será dado de baja del sistema',
      icon: 'error',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, estoy seguro!'
    }).then((result) => {
      if (result.isConfirmed) {
        var socioBajaDTO: SocioBajaDTO = {
          idSocio: deudor.idSocio,
          idMotivoBaja: 1
        };
        this.socioService.deleteSocio(socioBajaDTO).subscribe(
          deleteResponse => {
            if(deleteResponse.success){
              this.deudoresDTO.splice(this.deudoresDTO.lastIndexOf(deudor), 1);
              Swal.fire({
                icon: 'success',
                title: 'Baja correcta!',
                titleText: 'El socio ' + deudor.apellido + ' ' + deudor.nombre + ' ha sido dado de baja por morosidad'
              })
            } else {
              Swal.fire(
                'Error',
                'Se produjo un error. Intente nuevamente',
                'error'
              );
            }
          },
          errorDelete => {
            if (errorDelete.status === 401){
              Swal.fire(
                'Error',
                'Se produjo un error. Intente nuevamente',
                'error'
              );
              this.router.navigate(['/'+PageEnum.AUTH]);
            }else{
              console.log('ERROR', errorDelete);
            }
          },
        );

      }
    })
  }

  compareDate(fechaPrimerCbte: string): boolean {
    let a = moment(fechaPrimerCbte).isBefore(new Date());
    return a;
  }

}
