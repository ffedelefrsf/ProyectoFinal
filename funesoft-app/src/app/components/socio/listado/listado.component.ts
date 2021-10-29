import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbDropdownConfig, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Socio } from '@app/model/socio';
import { SocioService } from '@app/services/socio.service';
import { PageEnum } from '@app/utils/page.enum';
import { DetalleSocioComponent } from '@app/components/socio/detalle/detalle.component';
import { BajaSocioComponent } from '@app/components/socio/baja/baja.component';
import { NgxSpinnerService } from 'ngx-spinner';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoSocioComponent implements OnInit {

  loading: boolean = false;
  sociosArray: Socio[];
  error: boolean = false;
  public sidebarOpened = false;
  currentDate: String;

  filterDatos = '';

  constructor(private socioService: SocioService,
              private router: Router,
              private modalService: NgbModal,
              config: NgbDropdownConfig,
              private spinner: NgxSpinnerService) 
    {config.placement = 'bottom-right'; }

  ngOnInit() {
    this.currentDate = new DatePipe("en-US").transform(new Date(), 'yyyy-MM-dd');
    this.getSocios();
  }

  getSocios(){
    this.loading = true;
    let socio: Socio = {
      id: null,
    };
    this.socioService.getSocios(socio).subscribe(
      response => {
        this.sociosArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.sociosArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.sociosArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  editSocio(socio: Socio){
    this.router.navigate(['/'+PageEnum.SOCIO_MODIFICACION], { 
      state: { socio: JSON.stringify(socio) } 
    });
  }

  detalleSocio(socio: Socio){
    const modalRef = this.modalService.open(DetalleSocioComponent, { size: 'xl' });
    modalRef.componentInstance.socio = socio;
  }

  deleteSocio(socio: Socio){
    const modalRef = this.modalService.open(BajaSocioComponent, { size: 'xl' });
    modalRef.componentInstance.socio = socio;
    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
        if (receivedEntry){
          socio.estado = {
            id: 2,
            nroEstado: 2,
            descripcion: 'BAJA'
          };
        }
      });
  }

  toggleOffcanvas() {
    this.sidebarOpened = !this.sidebarOpened;
    if (this.sidebarOpened) {
      document.querySelector('.sidebar-offcanvas').classList.add('active');
    }
    else {
      document.querySelector('.sidebar-offcanvas').classList.remove('active');
    }
  }

  downloadPDF() {
      Swal.fire({
        icon: 'success',
        title: '¡Generado!',
        titleText: 'Preparando la descarga del .PDF',
        showConfirmButton: false,
        timer: 1500
      })
      this.socioService.getPDF()
      .subscribe(
        (data: Blob) => {
          var file = new Blob([data], { type: 'application/pdf' })
          var fileURL = URL.createObjectURL(file);
  
          window.open(fileURL); 
          var a         = document.createElement('a');
          a.href        = fileURL; 
          a.target      = '_blank';
          a.download    = 'padrón de socios - ' + this.currentDate + '.pdf';
          document.body.appendChild(a);
          a.click();
        },
        (error) => {
          console.log('getPDF error: ',error);
        }
      );
  }

  downloadXLS() {
    Swal.fire({
      icon: 'success',
      title: '¡Generado!',
      titleText: 'El archivo .XLS se guardó en el dispositivo',
      showConfirmButton: false,
      timer: 1500
    })
    this.socioService.getXLS()
    .subscribe();
  }

}
