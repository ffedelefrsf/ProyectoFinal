import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.scss']
})
export class SideMenuComponent implements OnInit {

  socioGroup: boolean = true;
  adherenteGroup: boolean = true;
  tarifaGroup: boolean = true;
  zonaGroup: boolean = true;
  cobradorGroup: boolean = true;
  obraSocialGroup: boolean = true;
  comprobanteGroup: boolean = true;
  pagoGroup: boolean = true;

  constructor() { }

  ngOnInit() {
  }

}
