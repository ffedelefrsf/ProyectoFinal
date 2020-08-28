import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-alta-zona',
  templateUrl: './alta-zona.component.html',
  styleUrls: ['./alta-zona.component.scss']
})
export class AltaZonaComponent implements OnInit {

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  disableField: boolean = false;

  constructor() { }


  ngOnInit() {

  }

  createZona() {
  }

  onCancel() {
  }

}
