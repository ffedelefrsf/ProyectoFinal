import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/merge';
import 'rxjs/add/operator/filter';


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

  constructor() { }

  search = (text$: Observable<string>) =>
    text$
      .debounceTime(200)
      .distinctUntilChanged()
      .map(term => term.length > 1 ? []
        : planes.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));

  @ViewChild('instance', {static: true}) instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();

  focusSearch = (text$: Observable<string>) =>
  text$
    .debounceTime(200).distinctUntilChanged()
    .merge(this.focus$)
    .merge(this.click$.filter(() => !this.instance.isPopupOpen()))
    .map(term => (term === '' ? planes : planes.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10));

  ngOnInit() {
    this.currentRate = 8;
  }

}
