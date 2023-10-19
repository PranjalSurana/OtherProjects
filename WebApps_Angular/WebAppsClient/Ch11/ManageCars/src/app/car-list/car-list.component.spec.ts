import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarListComponent } from './car-list.component';
import { of } from 'rxjs';
import { CarService } from '../cars/car.service';

describe('CarListComponent', () => {
  let component: CarListComponent;
  let fixture: ComponentFixture<CarListComponent>;

  let mockCarService = jasmine.createSpyObj('CarService', ['getCars']);

  const mockCars = [
    {
    "doors": 2,
    "make": "Ferrari",
    "model": "F40",
    "price": 1500000,
    "year": 2022
    },
    {
    "doors": 2,
    "make": "Tesla",
    "model": "Roadster",
    "price": 220000,
    "year": 2017
    },
    {
    "doors": 5,
    "make": "Chrysler",
    "model": "Pacifica Hybrid",
    "price": 60000,
    "year": 2024
    }
  ];

  mockCarService.getCars.and.returnValue(of(mockCars));


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarListComponent ],
      providers: [
        { 
          provide: CarService, useValue: mockCarService
        }
      ]
        
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // it('should check if there is data in table', () => {
  //   const tableRows = fixture.nativeElement.querySelectorAll('tr');
  //   expect(tableRows.length).toBe(4);
  // });

  it('should retrieve books from the service', () => {
    expect(component.carlist.length).toBeGreaterThanOrEqual(3);
   }); 

  it('should expect for make of 1st car to be Ferrari', () => {    
    expect(component.carlist[0].make).toBe('Ferrari')
  })

  it('should expect for model of 3rd car to be Pacifica Hybrid', () => {    
    expect(component.carlist[2].model).toBe('Pacifica Hybrid')
  })
   

  // it('should check if 1st element is a Tesla model', () => {    
  //   const tableRows = fixture.nativeElement.querySelectorAll('tr');
  //   const firstCellText = tableRows[1].querySelector('td').textContent;
  //   expect(firstCellText).toContain('Tesla'); 
  // });

});

// TODO: Type of test?

// 'should display a list of items': This test sets the items input of the component, triggers change detection, and then checks if the list elements (<li>) are correctly displayed with the expected content.

// 'should handle an empty list': This test checks if the component handles an empty list by ensuring that no list elements are displayed.

// 'should handle null or undefined input': This test checks if the component handles null or undefined input by making sure no list elements are displayed in these cases.

// ------------------------------------------------------------------------------

// it('should handle an empty list', () => {
//   const items: string[] = [];
//   component.items = items;
//   fixture.detectChanges();

//   const listItemElements = fixture.nativeElement.querySelectorAll('li');
//   expect(listItemElements.length).toBe(0);
// });

// it('should handle null or undefined input', () => {
//   component.items = null;
//   fixture.detectChanges();
//   const listItemElements = fixture.nativeElement.querySelectorAll('li');
//   expect(listItemElements.length).toBe(0);

//   component.items = undefined;
//   fixture.detectChanges();
//   const listItemElements2 = fixture.nativeElement.querySelectorAll('li');
//   expect(listItemElements2.length).toBe(0);
// });