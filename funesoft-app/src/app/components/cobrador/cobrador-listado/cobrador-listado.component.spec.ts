import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CobradorListadoComponent } from './cobrador-listado.component';

describe('CobradorListadoComponent', () => {
  let component: CobradorListadoComponent;
  let fixture: ComponentFixture<CobradorListadoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CobradorListadoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CobradorListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
