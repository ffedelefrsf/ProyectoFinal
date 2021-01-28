import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObraSocialListadoComponent } from './obra-social-listado.component';

describe('ObraSocialListadoComponent', () => {
  let component: ObraSocialListadoComponent;
  let fixture: ComponentFixture<ObraSocialListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObraSocialListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObraSocialListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
