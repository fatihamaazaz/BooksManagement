import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookTrucksComponent } from './tracks.component';

describe('BookTrucksComponent', () => {
  let component: BookTrucksComponent;
  let fixture: ComponentFixture<BookTrucksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BookTrucksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookTrucksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
