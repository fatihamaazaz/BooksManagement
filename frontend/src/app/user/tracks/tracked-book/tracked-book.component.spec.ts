import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackedBookComponent } from './tracked-book.component';

describe('TrackedBookComponent', () => {
  let component: TrackedBookComponent;
  let fixture: ComponentFixture<TrackedBookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TrackedBookComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TrackedBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
