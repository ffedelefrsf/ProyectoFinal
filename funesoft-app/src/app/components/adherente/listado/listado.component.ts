import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbDropdownConfig, NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/observable';

import { Adherente } from '@app/model/adherente';
import { AdherenteService } from '@app/services/adherente.service';
import { PageEnum } from '@app/utils/page.enum';
import { DetalleAdherenteComponent } from '@app/components/adherente/detalle/detalle.component';
import { BajaAdherenteComponent } from '@app/components/adherente/baja/baja.component';
import { SocioService } from '@app/services/socio.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/merge';
import 'rxjs/add/operator/filter';
import { GetAdherenteBySocioDNIRequestDTO } from '@app/dtos/getAdherenteBySocioDNIRequest.dto';
import { DatePipe } from '@angular/common';
import Swal from 'sweetalert2/dist/sweetalert2.js';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoAdherenteComponent implements OnInit {

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;

  loading: boolean = false;
  adherenteArray: Adherente[];
  error: boolean = false;
  currentRate: any;
  dniSocios: string[];
  public typeaheadBasicModel: any;
  public sidebarOpened = false;
  currentDate: String;
  filterDatos = '';

  constructor(private adherenteService: AdherenteService,
              private socioService: SocioService,
              private router: Router,
              private modalService: NgbModal,
              config: NgbDropdownConfig) 
    {config.placement = 'bottom-right'; }

  ngOnInit() {
    this.currentDate = new DatePipe("en-US").transform(new Date(), 'yyyy-MM-dd');
    this.currentRate = 8;
    this.getSociosDNI();
    this.getAdherentes();
  }

  getSociosDNI(){
    this.loading = true;
    this.socioService.getDNIs().subscribe(
      response => {
        this.dniSocios = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.adherenteArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.adherenteArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  search = (text$: Observable<string>) => 
    text$
      .debounceTime(200)
      .distinctUntilChanged()
      .map(term => this.dniSocios.filter(v => v.indexOf(term) > -1).slice(0, 10));

  onSearchChange(searchValue: string){
    this.loading = true;
    var getAdherenteBySocioDNIRequestDTO: GetAdherenteBySocioDNIRequestDTO;
    if (searchValue != '') {
      getAdherenteBySocioDNIRequestDTO = {
        dniSocio: searchValue
      };
    } else {
      getAdherenteBySocioDNIRequestDTO = null;
    }
    
    this.adherenteService.getAdherentesOrdered(getAdherenteBySocioDNIRequestDTO).subscribe(
      response => {
        this.adherenteArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.adherenteArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.adherenteArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  getAdherentes(){
    this.loading = true;
    this.adherenteService.getAdherentesOrdered().subscribe(
      response => {
        this.adherenteArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.adherenteArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.adherenteArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

  editAdherente(adherente: Adherente){
    this.router.navigate(['/'+PageEnum.ADHERENTE_MODIFICACION], { 
      state: { adherente: JSON.stringify(adherente) } 
    });
  }

  detalleAdherente(adherente: Adherente){
    const modalRef = this.modalService.open(DetalleAdherenteComponent, { size: 'xl' });
    modalRef.componentInstance.adherente = adherente;
  }

  deleteAdherente(adherente: Adherente){
    const modalRef = this.modalService.open(BajaAdherenteComponent, { size: 'xl' });
    modalRef.componentInstance.adherente = adherente;
    modalRef.componentInstance.passEntry.subscribe((receivedEntry) => {
        if (receivedEntry){
          adherente.estado = {
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
    this.adherenteService.getPDF()
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
  this.adherenteService.getXLS()
  .subscribe();
}

}
