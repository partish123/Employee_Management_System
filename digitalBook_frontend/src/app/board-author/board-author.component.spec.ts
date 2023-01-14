import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardAuthorComponent } from './board-author.component';

describe('BoardAuthorComponent', () => {
  let component: BoardAuthorComponent;
  let fixture: ComponentFixture<BoardAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoardAuthorComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoardAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
