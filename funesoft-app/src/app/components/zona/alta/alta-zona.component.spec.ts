import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AltaZonaComponent } from './alta-zona.component';

describe('AltaZonaComponent', () => {
  let component: AltaZonaComponent;
  let fixture: ComponentFixture<AltaZonaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AltaZonaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AltaZonaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
