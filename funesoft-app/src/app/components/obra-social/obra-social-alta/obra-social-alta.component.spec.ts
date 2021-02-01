import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObraSocialAltaComponent } from './obra-social-alta.component';

describe('ObraSocialAltaComponent', () => {
  let component: ObraSocialAltaComponent;
  let fixture: ComponentFixture<ObraSocialAltaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObraSocialAltaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObraSocialAltaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
