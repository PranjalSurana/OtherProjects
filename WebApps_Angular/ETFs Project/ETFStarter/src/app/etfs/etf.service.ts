import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Etfs } from '../models/etfs';

@Injectable({
  providedIn: 'root'
})
export class EtfService {

  private url: string;

  constructor(private httpClient: HttpClient) {
    this.url = 'http://localhost:8080/etfs?filter=';
  }

  // TODO: Use getEtfsByFilter(), instead of 4 different functions
  getEtfs(): Observable<Etfs[]> {
    // return of(this.mockEtfs);
    return this.httpClient.get<Etfs[]>(this.url);
  }

  getETFsBy3MTR(): Observable<Etfs[]> {
    return this.httpClient.get<Etfs[]>(this.url + 'ThreeMoTR');
  }
  
  getETFsByER(): Observable<Etfs[]> {
    return this.httpClient.get<Etfs[]>(this.url + 'Expense_Ratio');
  }

  getETFsyAUMBill(): Observable<Etfs[]> {
    return this.httpClient.get<Etfs[]>(this.url + 'AUM_bil');
  }

}
