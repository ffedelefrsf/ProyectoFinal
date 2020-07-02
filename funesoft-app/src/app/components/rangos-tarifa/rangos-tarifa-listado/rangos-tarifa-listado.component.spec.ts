import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RangosTarifaListadoComponent } from './rangos-tarifa-listado.component';

describe('RangosTarifaListadoComponent', () => {
  let component: RangosTarifaListadoComponent;
  let fixture: ComponentFixture<RangosTarifaListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RangosTarifaListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RangosTarifaListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
