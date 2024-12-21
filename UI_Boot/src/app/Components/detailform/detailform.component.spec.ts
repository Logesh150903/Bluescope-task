import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailformComponent } from './detailform.component';

describe('DetailformComponent', () => {
  let component: DetailformComponent;
  let fixture: ComponentFixture<DetailformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DetailformComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
