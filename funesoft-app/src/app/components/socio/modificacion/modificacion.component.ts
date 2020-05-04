import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Socio } from '@app/model/socio';

@Component({
  selector: 'app-modificacion',
  templateUrl: './modificacion.component.html',
  styleUrls: ['./modificacion.component.scss']
})
export class ModificacionComponent implements OnInit {

  socio: Socio;

  constructor(private router: Router) {
    this.socio = JSON.parse(this.router.getCurrentNavigation().extras.state.socio);
  }

  ngOnInit() {
    console.log(this.socio);
  }

}
