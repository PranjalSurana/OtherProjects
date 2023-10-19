import { Component, OnInit } from '@angular/core';
import { Car } from '../models/car';
import { CarService } from '../cars/car.service';

@Component({
  selector: 'app-car-list',
  templateUrl: './car-list.component.html',
  styleUrls: ['./car-list.component.css']
})
export class CarListComponent  implements OnInit {

  carlist: Car[] = [];

  // showAllCars: boolean = false;
  // showCarsByPrice: boolean = false;

  constructor(private carService: CarService) {}

  ngOnInit() {
    this.loadAllCars()
  }

  // carlist: Car[] = [
  //   {
  //     "doors": 2,
  //     "make": "Tesla",
  //     "model": "Roadster",
  //     "price": 220000,
  //     "year": 2017
  //   },
  //   {
  //     "doors": 2,
  //     "make": "Ferrari",
  //     "model": "F40",
  //     "price": 1500000,
  //     "year": 2022
  //   }
  // ];

  // For the E2E testing, click on 1 button, get the result table and check for content.
  // Calling 1 table by rewriting cars list is correct way
  // Negative test cases checking in E2E is optional, not an industry standard though.

  loadAllCars(): void {
    this.carService.getCars().subscribe(cars => {
      this.carlist = cars;
    });
    // this.showAllCars = true;
    // this.showCarsByPrice = false;
  }

  loadCarsByPrice(): void {
    this.carService.getCarsByPrice().subscribe(cars => {
      this.carlist = cars;
    });
    // this.showCarsByPrice = true;
    // this.showAllCars = false;
  }

  loadCarsByYear(): void {
    this.carService.getCarsByYear().subscribe(cars => {
      this.carlist = cars;
    });
    // this.showCarsByPrice = true;
    // this.showAllCars = false;
  }

  loadCarsByDoors(): void {
    this.carService.getCarsByDoors().subscribe(cars => {
      this.carlist = cars;
    });
    // this.showCarsByPrice = true;
    // this.showAllCars = false;
  }

}