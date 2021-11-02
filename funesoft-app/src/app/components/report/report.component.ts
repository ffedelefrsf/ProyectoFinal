import { Component, OnInit } from '@angular/core';
import { EstadisticaService } from '@app/services/estadistica.service';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  currentDate: Date;

  constructor(
    private estadisticaService: EstadisticaService,
    private spinner: NgxSpinnerService,
  ) { }

  ngOnInit() {
  }

  generarReporte1() {
    Swal.fire({
      icon: 'success',
      title: 'Reporte generado!',
      titleText: 'Preparando descarga...',
      showConfirmButton: false,
      timer: 1500
    });
    this.estadisticaService.generarReporteSociosEstados()
    .subscribe(
      (data: Blob) => {
        var file = new Blob([data], { type: 'application/pdf' })
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
        var a         = document.createElement('a');
        a.href        = fileURL;
        a.target      = '_blank';
        a.download    = 'Estados de socios - ' + this.currentDate + '.pdf';
        document.body.appendChild(a);
        a.click();
      },
      (error) => {
        console.log('getPDF error: ',error);
      }
    );
  }
  
  generarReporte2() {
    Swal.fire({
      icon: 'success',
      title: 'Reporte generado!',
      titleText: 'Preparando descarga...',
      showConfirmButton: false,
      timer: 1500
    });
    this.estadisticaService.generarReporteCbtesyPagos()
    .subscribe(
      (data: Blob) => {
        var file = new Blob([data], { type: 'application/pdf' })
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
        var a         = document.createElement('a');
        a.href        = fileURL;
        a.target      = '_blank';
        a.download    = 'Comprobantes y pagos - ' + this.currentDate + '.pdf';
        document.body.appendChild(a);
        a.click();
      },
      (error) => {
        console.log('getPDF error: ',error);
      }
    );
  }

}
