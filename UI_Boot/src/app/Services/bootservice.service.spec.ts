import { TestBed } from '@angular/core/testing';

import { BootserviceService } from './bootservice.service';

describe('BootserviceService', () => {
  let service: BootserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BootserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
