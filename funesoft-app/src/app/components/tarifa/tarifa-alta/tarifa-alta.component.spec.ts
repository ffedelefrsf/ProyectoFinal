import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TarifaAltaComponent } from './tarifa-alta.component';

describe('TarifaAltaComponent', () => {
  let component: TarifaAltaComponent;
  let fixture: ComponentFixture<TarifaAltaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TarifaAltaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TarifaAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
