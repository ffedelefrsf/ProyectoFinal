import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VentaAltaComponent } from './venta-alta.component';

describe('VentaAltaComponent', () => {
  let component: VentaAltaComponent;
  let fixture: ComponentFixture<VentaAltaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VentaAltaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VentaAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
