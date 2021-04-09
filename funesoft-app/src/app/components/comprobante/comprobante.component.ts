import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import Swal from 'sweetalert2/dist/sweetalert2.js';

import { ComprobanteService } from '@app/services/comprobante.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Comprobante } from '@app/model/comprobante';
import { ComprobanteDTO } from '@app/dtos/comprobante.dto';
import { PageEnum } from '@app/utils/page.enum';


@Component({
  selector: 'app-comprobante',
  templateUrl: './comprobante.component.html',
  styleUrls: ['./comprobante.component.scss']
})
export class ComprobanteComponent implements OnInit {

  generados: boolean = false;
  impresos: boolean = false;
  loadingGeneracion: boolean = false;
  descargando: boolean = false;
  success: boolean = false;
  error: boolean = false;
  currentDate: Date;
  comprobantesDTO: ComprobanteDTO[] = [];
  showTable: boolean = false;

  constructor(private router: Router,
    private http: HttpClient,
    private comprobanteService: ComprobanteService,
    private spinner: NgxSpinnerService) {
      this.generados = false;
      this.impresos = false;
      this.loadingGeneracion = false;
      this.descargando = false;
      this.success = false;
      this.error = false;
  }

  ngOnInit() {
    this.currentDate = new Date();
  }

  generarComprobantes(){

    Swal.fire({
      title: 'Estás seguro?',
      text: "Se generarán los comprobantes del mes actual",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, estoy seguro!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.comprobanteService.generarComprobantesMasivos().subscribe(
          response => {
            if (response.success){
              this.success = true;
              this.error = false;
              this.loadingGeneracion = false;
              this.generados = true;
              this.imprimirComprobantes();
              Swal.fire({
                icon: 'success',
                title: 'Generados!',
                titleText: 'Preparando descarga...',
                showConfirmButton: false,
                timer: 1500
              })
            }else{
              this.loadingGeneracion = false;
              this.error = true;
              this.success = false;
              this.generados = false;
              Swal.fire(
                'Error',
                'Se produjo un error. Intente nuevamente',
                'error'
              );
            }
          },
          err => {
            this.loadingGeneracion = false;
            this.error = true;
            this.generados = false;
            this.impresos = false;
            Swal.fire(
              'Error',
              'Se produjo un error. Intente nuevamente',
              'error'
            );
          }
        );
      }
    })
    
  }

  getAllComprobantes() {
    this.spinner.show();
    let comprobante: Comprobante = {
    };
    this.comprobanteService.getAll(comprobante).subscribe(
      response => {
        this.comprobantesDTO = response.data.sort((a,b) => b.nroComprobante - a.nroComprobante);
        this.error = false;
        this.showTable = true;
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
        this.spinner.hide();
      }
    );
  }

  imprimirComprobantes() {
    this.descargando = true;
    this.getPDF();
    this.generados = false;
    this.impresos = true;
  }


  getPDF(){
    this.spinner.show();
    this.comprobanteService.getPDF()
    .subscribe(
      (data: Blob) => {
        var file = new Blob([data], { type: 'application/pdf' })
        var fileURL = URL.createObjectURL(file);

        window.open(fileURL); 
        var a         = document.createElement('a');
        a.href        = fileURL; 
        a.target      = '_blank';
        a.download    = 'comprobantes - ' + this.currentDate + '.pdf';
        document.body.appendChild(a);
        a.click();
      },
      (error) => {
        console.log('getPDF error: ',error);
      }
    );
  }


}
