import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Car } from '../models/car';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private url: string;

  // mockCars: Car[] = [
  //   {
  //     "doors": 3,
  //     "make": "Daimler AG",
  //     "model": "Smart C453",
  //     "price": 23000,
  //     "year": 2023
  //   },
  //   {
  //     "doors": 5,
  //     "make": "Chrysler",
  //     "model": "Pacifica Hybrid",
  //     "price": 60000,
  //     "year": 2024
  //   }
  // ];

  constructor(private httpClient: HttpClient) {
    this.url = 'http://localhost:8080/CarService/jaxrs/cars?filter=';
  }


  // TODO: Use getCarsByFilter(), instead of 4 different functions
  getCars(): Observable<Car[]> {
    // return of(this.mockCars);
    return this.httpClient.get<Car[]>(this.url);
  }

  getCarsByPrice(): Observable<Car[]> {
    return this.httpClient.get<Car[]>(this.url + 'price');
  }

  getCarsByYear(): Observable<Car[]> {
    return this.httpClient.get<Car[]>(this.url + 'year');
  }

  getCarsByDoors(): Observable<Car[]> {
    return this.httpClient.get<Car[]>(this.url + 'doors');
  }
  
}