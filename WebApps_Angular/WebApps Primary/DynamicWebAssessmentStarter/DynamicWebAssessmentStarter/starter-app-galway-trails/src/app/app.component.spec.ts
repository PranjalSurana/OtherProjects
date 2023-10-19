import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import jasmine from 'jasmine';
import { GalwaytrailsComponent } from './galwaytrails/galwaytrails.component';
import { Component } from '@angular/core';

@Component({
  selector: 'app-galwaytrails',
  template: '<p>Mock Galway Trail List Component</p>'
})

class MockGalwayTrailComponent {}

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        AppComponent,
        MockGalwayTrailComponent
      ],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'Galway Trails'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('Galway Trails');
  });

});
