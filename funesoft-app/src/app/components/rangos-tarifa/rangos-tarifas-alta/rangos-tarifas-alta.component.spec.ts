import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RangosTarifasAltaComponent } from './rangos-tarifas-alta.component';

describe('RangosTarifasAltaComponent', () => {
  let component: RangosTarifasAltaComponent;
  let fixture: ComponentFixture<RangosTarifasAltaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RangosTarifasAltaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RangosTarifasAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
