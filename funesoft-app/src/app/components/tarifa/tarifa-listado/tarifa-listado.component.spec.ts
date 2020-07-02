import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TarifaListadoComponent } from './tarifa-listado.component';

describe('TarifaListadoComponent', () => {
  let component: TarifaListadoComponent;
  let fixture: ComponentFixture<TarifaListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TarifaListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TarifaListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
