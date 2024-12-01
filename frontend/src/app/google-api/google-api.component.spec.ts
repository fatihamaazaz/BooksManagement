import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GoogleApiComponent } from './google-api.component';

describe('GoogleApiComponent', () => {
  let component: GoogleApiComponent;
  let fixture: ComponentFixture<GoogleApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GoogleApiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GoogleApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
