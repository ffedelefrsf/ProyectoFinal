import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { ComprobanteService } from '@app/services/comprobante.service';


@Component({
  selector: 'app-comprobante',
  templateUrl: './comprobante.component.html',
  styleUrls: ['./comprobante.component.scss']
})
export class ComprobanteComponent implements OnInit {

  generados: boolean = false;
  impresos: boolean = false;
  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  currentDate: Date;

  constructor(private router: Router,
    private http: HttpClient,
    private comprobanteService: ComprobanteService) {
  }

  ngOnInit() {
    this.currentDate = new Date();
    this.loading = false;
  }

  generarComprobantes(event: any){
    this.loading = true;
    this.impresos = false;
    this.comprobanteService.generarComprobantesMasivos().subscribe(
      response => {
        if (response.success){
          this.success = true;
          this.error = false;
          this.loading = false;
          this.generados = true;
        }else{
          this.loading = false;
          this.error = true;
        }
      },
      err => {
        this.loading = false;
        this.error = true;
        this.generados = false;
        this.impresos = false;
      }
    );
    
  }

  imprimirComprobantes(event: any){
    this.getPDF();
    this.generados = false;
    this.impresos = true;
  }


  getPDF(){
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
