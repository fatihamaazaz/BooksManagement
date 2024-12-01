import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LikedBookComponent } from './liked-book.component';

describe('LikedBookComponent', () => {
  let component: LikedBookComponent;
  let fixture: ComponentFixture<LikedBookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LikedBookComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LikedBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
