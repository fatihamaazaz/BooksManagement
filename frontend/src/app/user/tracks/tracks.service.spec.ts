import { TestBed } from '@angular/core/testing';

import { TrackedBooksService } from './tracks.service';

describe('TrackedBooksService', () => {
  let service: TrackedBooksService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrackedBooksService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
