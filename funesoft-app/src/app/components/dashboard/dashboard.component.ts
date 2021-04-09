import { Component, OnInit } from '@angular/core';
import { ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { EstadisticaDTO } from '@app/dtos/estadistica.dto';
import { EstadisticaService } from '@app/services/estadistica.service';
import { PageEnum } from '@app/utils/page.enum';
import { NgxSpinnerService } from 'ngx-spinner';

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

  constructor(
    private estadisticaService: EstadisticaService,
    private spinner: NgxSpinnerService,
    private router: Router
  ) { }

  ngOnInit() {
    this.setDates();
    this.getEstadisticas();
  }

  setDates() {
    const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Deciembre"
    ];

    const date = new Date();
    this.currentDate = date.getUTCDate() + " de " + monthNames[date.getMonth()] + ", " + date.getFullYear();
    this.currentDay = date.toLocaleDateString('es-AR', { weekday: 'long' });
  }

  getEstadisticas(){

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

    this.estadisticaService.getEstadisticas();
  }

}
