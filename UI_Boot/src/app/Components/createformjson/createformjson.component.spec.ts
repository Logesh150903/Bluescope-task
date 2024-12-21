import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateformjsonComponent } from './createformjson.component';

describe('CreateformjsonComponent', () => {
  let component: CreateformjsonComponent;
  let fixture: ComponentFixture<CreateformjsonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreateformjsonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateformjsonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
