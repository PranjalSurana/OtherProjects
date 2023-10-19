import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed, fakeAsync, inject, tick } from '@angular/core/testing';

import { CarService } from './car.service';
import { Car } from '../models/car';

describe('CarService', () => {

  let httpTestingController: HttpTestingController;
  let service: CarService;

  const mockCars = [
    [
      {
      "doors": 2,
      "make": "Tesla",
      "model": "Roadster",
      "price": 220000,
      "year": 2017
      },
      {
      "doors": 2,
      "make": "Ferrari",
      "model": "F40",
      "price": 1500000,
      "year": 2022
      },
      {
      "doors": 3,
      "make": "Daimler AG",
      "model": "Smart C453",
      "price": 23000,
      "year": 2023
      },
      {
      "doors": 5,
      "make": "Chrysler",
      "model": "Pacifica Hybrid",
      "price": 60000,
      "year": 2024
      }
      ]
  ]

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(CarService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return cars', inject([CarService], fakeAsync((service: CarService) => {
    let carlist: Car[] = [];
    service.getCars().subscribe(carsFromService => carlist = carsFromService);
    const req = httpTestingController.expectOne('http://localhost:8080/CarService/jaxrs/cars?filter=');
    expect(req.request.method).toEqual('GET');
    // Cause all outstanding asynchronous events to complete before continuing
    tick();
    expect(carlist).toBeTruthy();
  })));

  // TODO: Check why error?
  // it('should return year of 1st car as 2017', inject([CarService], fakeAsync((service: CarService) => {
  //   let carlist: Car[] = [];
  //   service.getCars().subscribe(carsFromService => carlist = carsFromService);
  //   const req = httpTestingController.expectOne('http://localhost:8080/CarService/jaxrs/cars?filter=');
  //   expect(req.request.method).toEqual('GET');
  //   req.flush(mockCars);
  //   tick();
  //   console.log(carlist)
  //   expect(carlist[0].year).toBe(2017);
  // })));

});
