import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { GalwayTrails } from '../models/galway-trails';

@Injectable({
  providedIn: 'root'
})
export class GalwayTrailService {

  url: string;

  constructor(private httpClient: HttpClient) {
    this.url = 'http://localhost:8080/trails?sortBy=';
  }

  getDefaultTrails(order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc' || order == '')
      return this.httpClient.get<GalwayTrails[]>(this.url).pipe(catchError(this.handleError));
    else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + '&sortAsc=false').pipe(catchError(this.handleError));
    else
      return this.httpClient.get<GalwayTrails[]>(this.url + '&howMany=3').pipe(catchError(this.handleError));
  }

  getTrailsSortedByName(order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'name').pipe(catchError(this.handleError));
    else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'name&sortAsc=false').pipe(catchError(this.handleError));
      else
        return this.httpClient.get<GalwayTrails[]>(this.url + 'name&howMany=3').pipe(catchError(this.handleError));
  }

  getTrailsSortedByLength (order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'length').pipe(catchError(this.handleError));
    else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'length&sortAsc=false').pipe(catchError(this.handleError));
      else
        return this.httpClient.get<GalwayTrails[]>(this.url + 'length&howMany=3').pipe(catchError(this.handleError));
  }

  getTrailsSortedByTimeToComplete(order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'time-to-complete').pipe(catchError(this.handleError));
    else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'time-to-complete&sortAsc=false').pipe(catchError(this.handleError));
      else
        return this.httpClient.get<GalwayTrails[]>(this.url + 'time-to-complete&howMany=3').pipe(catchError(this.handleError));
  }

  getTrailsSortedByDrivingTime(order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'driving-time').pipe(catchError(this.handleError));
      else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'driving-time&sortAsc=false').pipe(catchError(this.handleError));
      else
        return this.httpClient.get<GalwayTrails[]>(this.url + 'driving-time&howMany=3').pipe(catchError(this.handleError));
  }

  getTrailsSortedByDifficulty(order: string): Observable<GalwayTrails[]> {
    if(order == 'defaultAsc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'difficulty').pipe(catchError(this.handleError));
    else if (order == 'defaultDesc')
      return this.httpClient.get<GalwayTrails[]>(this.url + 'difficulty&sortAsc=false').pipe(catchError(this.handleError));
      else
        return this.httpClient.get<GalwayTrails[]>(this.url + 'difficulty&howMany=3').pipe(catchError(this.handleError));
  }

  handleError(response: HttpErrorResponse) {
    if (response.error instanceof ProgressEvent) {
      console.error('A client-side or network error occurred; ' + `${response.message} ${response.status} ${response.statusText}`);
    }
    else {
      console.error(`Backend returned code ${response.status}, ` + `body was: ${JSON.stringify(response.error)}`);
    }
    return throwError(
      () => 'Unable to contact service; please try again later.');
    }

}