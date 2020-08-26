import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/observable';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/merge';
import 'rxjs/add/operator/filter';
import { TarifaAltaDTO } from '@app/dtos/tarifaAlta.dto';
import { TarifaAltaRangosDTO } from '@app/dtos/tarifaAltaRangos.dto';

import { Plan } from '@app/model/plan';
import { TarifaService } from '@app/services/tarifa.service';
import { RangoTarifa } from '@app/model/rangoTarifa';

const planes = ['INDIVIDUAL', 'TITULAR Y ADHERENTE', 'FAMILIAR'];

@Component({
  selector: 'app-tarifa-alta',
  templateUrl: './tarifa-alta.component.html',
  styleUrls: ['./tarifa-alta.component.scss']
})
export class TarifaAltaComponent implements OnInit {

  currentRate: any;
  public typeaheadBasicModel: any;
  public typeaheadFocusModel: any;

  loading: boolean = false;
  success: boolean = false;
  error: boolean = false;
  disableField: boolean = false;

  altaTarifaForm: FormGroup;
  tarifaInsert: TarifaAltaDTO;

  rangos = new FormArray([]);
  location: any;

  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private tarifaService: TarifaService) {
    this.tarifaInsert = {};
  }

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  search = (text$: Observable<string>) =>
    text$
      .debounceTime(200)
      .distinctUntilChanged()
      .map(term => term.length > 1 ? []
        : planes.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

  focusSearch = (text$: Observable<string>) =>
  text$
    .debounceTime(200).distinctUntilChanged()
    .merge(this.focus$)
    .merge(this.click$.filter(() => !this.instance.isPopupOpen()))
    .map(term => (term === '' ? planes : planes.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10));


  ngOnInit() {

    this.altaTarifaForm = this.formBuilder.group({
      nroTarifa: this.formBuilder.control('0', [Validators.required, Validators.minLength(1), Validators.maxLength(5), Validators.pattern('^[0-9]*')]),
      descripcion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      plan: this.formBuilder.control('', [Validators.required, Validators.maxLength(20), Validators.pattern('^[a-z-ñ A-Z-Ñ]*')]),
      valor: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango1: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango2: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango3: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango4: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango5: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango6: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango7: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango8: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango9: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango10: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango11: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango12: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango13: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango14: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango15: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango16: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango17: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango18: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango19: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango20: this.formBuilder.control('0', [Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')]),
      valorRango21: this.formBuilder.control('0',[Validators.required, Validators.min(0), Validators.pattern('^-?[0-9]\\d*(\\.\\d{1,4})?$')])
    });

  }

  createTarifa(){

    if (!this.altaTarifaForm.invalid){

      var altaTarifaForm : TarifaAltaRangosDTO = this.altaTarifaForm.getRawValue();

      this.tarifaInsert.nroTarifa = altaTarifaForm.nroTarifa;
      this.tarifaInsert.descripcion = altaTarifaForm.descripcion;
      this.tarifaInsert.valor = altaTarifaForm.valor;

      switch (altaTarifaForm.plan) {
        case "INDIVIDUAL":
            var plan: Plan = {
              id: 1
            };
          break;
        
        case "TITULAR Y ADHERENTE":
            var plan: Plan = {
              id: 2
            };
          break;
      
        case "FAMILIAR":
            var plan: Plan = {
              id: 3
            };
          break;

        default:
          var plan: Plan = {
            id: 3
          };
      }

      this.tarifaInsert.plan = plan;

      const arr = [
        altaTarifaForm.valorRango1,
        altaTarifaForm.valorRango2,
        altaTarifaForm.valorRango3,
        altaTarifaForm.valorRango4,
        altaTarifaForm.valorRango5,
        altaTarifaForm.valorRango6,
        altaTarifaForm.valorRango7,
        altaTarifaForm.valorRango8,
        altaTarifaForm.valorRango9,
        altaTarifaForm.valorRango10,
        altaTarifaForm.valorRango11,
        altaTarifaForm.valorRango12,
        altaTarifaForm.valorRango13,
        altaTarifaForm.valorRango14,
        altaTarifaForm.valorRango15,
        altaTarifaForm.valorRango16,
        altaTarifaForm.valorRango17,
        altaTarifaForm.valorRango16,
        altaTarifaForm.valorRango18,
        altaTarifaForm.valorRango19,
        altaTarifaForm.valorRango20,
        altaTarifaForm.valorRango21
      ];

      var firstEdad = 0;
      this.tarifaInsert.listRango = [];

      arr.forEach(element => {

        var rango : RangoTarifa = {
          edadDesde: firstEdad == 0 ? firstEdad : firstEdad + 1,
          edadHasta: firstEdad + 5,
          valor: element
        };

      firstEdad = firstEdad + 5;

      this.tarifaInsert.listRango.push(rango);
        
      });

      this.tarifaService.createTarifa(this.tarifaInsert).subscribe(
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


    }else{
      this.error = true;
    }

  }

  onCancel(){
    //this.location.back();
  }

}
