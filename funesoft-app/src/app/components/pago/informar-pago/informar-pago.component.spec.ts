import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InformarPagoComponent } from './informar-pago.component';

describe('InformarPagoComponent', () => {
  let component: InformarPagoComponent;
  let fixture: ComponentFixture<InformarPagoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InformarPagoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InformarPagoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
