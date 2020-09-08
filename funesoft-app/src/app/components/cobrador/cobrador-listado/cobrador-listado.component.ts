import { Component, OnInit } from '@angular/core';
import { CobradorService } from '@app/services/cobrador.service';
import { Cobrador } from '@app/model/cobrador';
import { Router } from '@angular/router';
import { PageEnum } from '@app/utils/page.enum';

@Component({
  selector: 'app-cobrador-listado',
  templateUrl: './cobrador-listado.component.html',
  styleUrls: ['./cobrador-listado.component.scss']
})
export class CobradorListadoComponent implements OnInit {

  loading: boolean = false;
  error: boolean = false;
  cobradoresArray: Cobrador[];

  constructor(
    private cobradorService: CobradorService,
    private router: Router,
  ) { 
    
  }

  ngOnInit() {
    this.getCobradores();
  }

  getCobradores(){
    this.loading = true;
    let cobrador: Cobrador = {
      id: null,
    };
    this.cobradorService.getCobradores(cobrador).subscribe(
      response => {
        this.cobradoresArray = response.data;
        this.error = false;
      },
      error => {
        if (error.status === 401){
          this.router.navigate(['/'+PageEnum.AUTH]);
          this.cobradoresArray = [];
          this.error = true;
        }else{
          console.log('ERROR', error);
          this.cobradoresArray = [];
          this.error = true;
        }
      },
      () => {
        this.loading = false;
      }
    );
  }

}
