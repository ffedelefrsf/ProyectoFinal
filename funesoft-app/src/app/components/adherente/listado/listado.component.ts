import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbDropdownConfig } from '@ng-bootstrap/ng-bootstrap';

import { Adherente } from '@app/model/adherente';
import { AdherenteService } from '@app/services/adherente.service';
import { PageEnum } from '@app/utils/page.enum';
import { DetalleAdherenteComponent } from '@app/components/adherente/detalle/detalle.component';
import { BajaAdherenteComponent } from '@app/components/adherente/baja/baja.component';

@Component({
  selector: 'app-listado',
  templateUrl: './listado.component.html',
  styleUrls: ['./listado.component.scss']
})
export class ListadoAdherenteComponent implements OnInit {

  loading: boolean = false;
  adherenteArray: Adherente[];
  error: boolean = false;
  public sidebarOpened = false;

  constructor(private adherenteService: AdherenteService,
              private router: Router,
              private modalService: NgbModal,
              config: NgbDropdownConfig) 
    {config.placement = 'bottom-right'; }

  ngOnInit() {
    this.getSocios();
  }

  getSocios(){
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

}
