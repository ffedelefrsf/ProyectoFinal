import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import { Cobrador } from '@app/model/cobrador';
import { CobradorService } from '@app/services/cobrador.service';
import { PageEnum } from '@app/utils/page.enum';
import { Zona } from '@app/model/zona';
import { ZonaCobradoresDTO } from '@app/dtos/zonaCobradores.dto';
import { ZonaService } from '@app/services/zona.service';

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

  altaZonaForm: FormGroup;

  cobradoresArray: Cobrador[];
  cobradoresChecked: Cobrador[] = [];

  zonaInsert: Zona; 
  zonaCobradores: ZonaCobradoresDTO;

  constructor(
    private cobradorService: CobradorService,
    private router: Router,
    private formBuilder: FormBuilder,
    private zonaService: ZonaService
  ) { 
    this.zonaInsert = {};
    this.zonaCobradores = {};
  }

  ngOnInit() {
    this.getCobradores();
    console.log(this.cobradoresArray);
    this.altaZonaForm = this.formBuilder.group({
      nroZona: this.formBuilder.control('0', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('^[0-9]*')]),
      nombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)])
    });

  }

  getCobradores() {
    let cobrador: Cobrador = {
      id: null,
    };
    this.cobradorService.getCobradores(cobrador).subscribe(
      cobradorResponse => {
        this.cobradoresArray = cobradorResponse.data;
        this.loading = false;
      },
      errorCobrador => {
        if (errorCobrador.status === 401) {
          this.router.navigate(['/' + PageEnum.AUTH]);
          this.loading = false;
        } else {
          console.log('ERROR', errorCobrador);
        }
      }
    );

  }

  updateChecked(cobrador: Cobrador, event) {
    if (event.target.checked) {
      this.cobradoresChecked.push(cobrador);
    } else {
      const index = this.cobradoresChecked.findIndex(item => item.id === cobrador.id);
      this.cobradoresChecked.splice(index, 1);
    }
  }

  createZona() {
    if (!this.altaZonaForm.invalid) {
      var altaZonaForm: Zona = this.altaZonaForm.getRawValue();
      this.zonaInsert.nombre = altaZonaForm.nombre;
      this.zonaInsert.nroZona = altaZonaForm.nroZona;

      this.zonaCobradores.zona = this.zonaInsert;
      this.zonaCobradores.cobradores = this.cobradoresChecked;

      this.zonaService.createZona(this.zonaCobradores).subscribe(
        response => {
          if (response.success){
            this.success = true;
            this.error = false;
            this.loading = false;
          }else{
            this.loading = false;
            this.error = true;
          }
        },
        err => {
          this.loading = false;
          this.error = true;
        }
      );

    }
  }

  onCancel() {
  }

}
