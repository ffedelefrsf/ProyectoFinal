import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CobradorAltaComponent } from './cobrador-alta.component';

describe('CobradorAltaComponent', () => {
  let component: CobradorAltaComponent;
  let fixture: ComponentFixture<CobradorAltaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CobradorAltaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CobradorAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
