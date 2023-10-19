import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GalwaytrailsComponent } from './galwaytrails.component';
import { of } from 'rxjs';
import { GalwayTrailService } from '../service/galway-trail.service';

describe('GalwaytrailsComponent', () => {
  let component: GalwaytrailsComponent;
  let fixture: ComponentFixture<GalwaytrailsComponent>;  

  let mockTrailService = jasmine.createSpyObj('GalwayTrailService', ['getDefaultTrails', 'getTrailsSortedByName', 'getTrailsSortedByLength', 'getTrailsSortedByTimeToComplete', 'getTrailsSortedByDrivingTime', 'getTrailsSortedByDifficulty']);

  const mockTrails = [
    {
      "name": "Slieve Carran Brown and Yellow Trails",
      "length": 4.7,
      "timeToComplete": 2.5,
      "drivingTime": 55,
      "difficulty": "Easy walking trail",
      "comments": "Popular for its gorgeous wildflowers that can be seen along the trails in the spring and summertime"
      },
      {
      "name": "Rinville Park Loop",
      "length": 2.3,
      "timeToComplete": 1,
      "drivingTime": 0,
      "difficulty": "Easy trail",
      "comments": "It's right in Galway City and provides excellent natural views. It is filled with gorgeous wildflowers and birds"
      },
      {
      "name": "Portumna Forest Park",
      "length": 15,
      "timeToComplete": 4.4,
      "drivingTime": 57,
      "difficulty": "Moderate loop trail",
      "comments": "A breathtaking area that features an abbey and castle, both from a few hundred years ago!"
      }
  ]

  mockTrailService.getDefaultTrails.and.returnValue(of(mockTrails));

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GalwaytrailsComponent ],
      providers: [
        { 
          provide: GalwayTrailService, useValue: mockTrailService
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GalwaytrailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update as Asc option of is trailByTime selected', () => {
    const selectElement = fixture.nativeElement.querySelector('#trailByTimeToComplete');
    const option = fixture.nativeElement.querySelector('option[value="defaultAsc"]');
    selectElement.value = 'defaultAsc';
    selectElement.dispatchEvent(new Event('change'));
    expect(component.selectedValue).toBe('defaultAsc');
  });

  it('should retrieve top 3 elements of trailByName', () => {
    const selectElement = fixture.nativeElement.querySelector('#trailByName');
    const option = fixture.nativeElement.querySelector('option[value="customvalue"]');
    selectElement.value = 'customvalue';
    selectElement.dispatchEvent(new Event('change'));
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    const table = compiled.querySelector('table');
    console.log(table);
    expect(table.rows.length).toBe(4);
  });

  it('defaultTrail with top 3 rows option should have 3 rows',()=>{
    const selectElement = fixture.nativeElement.querySelector('#trailByName');
    const option = fixture.nativeElement.querySelector('option[value="customvalue"]');
    selectElement.value = 'customvalue';
    selectElement.dispatchEvent(new Event('change'));
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    const table = compiled.querySelector('table');
    console.log(table);
    expect(table.rows.length).toBe(4);
  });


  it('should retrieve Trails from the service', () => {
    expect(mockTrails.length).toBeGreaterThanOrEqual(3);
  });

 

});