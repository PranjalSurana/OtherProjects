import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Events } from '../models/events';

@Injectable({
  providedIn: 'root'
})
export class EventsService {

  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8082/api/event';
  }

  getEvents(): Observable<Events[]> {    
    return this.http.get<Events[]>("http://localhost:8082/api/events");
  }

  addEvent(newEvent: Events): Observable<Events> {
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
    });
    return this.http.post<Events>(this.url, newEvent, { headers: httpHeaders });
  }

  getEventById(id: number) { 
    this.http.get<Events>(this.url + id);
  }

  updateEventById(id: number): Observable<Events> {  // PUT /api/event/:id
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
    });
    return this.http.put<Events>(this.url + "/" + id, id, { headers: httpHeaders });
  }

  incrementLikes(id: number): Observable<Events> {  // PUT /api/event/:id/like
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
    });
    return this.http.put<Events>(this.url + "/" + id + "/like", id, { headers: httpHeaders });
  }

  incrementDislikes(id: number): Observable<Events> {  // PUT /api/event/:id/dislike
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
    });
    return this.http.put<Events>(this.url + "/" + id + "/dislike", id, { headers: httpHeaders });
  }

  deleteEvent(id: number) {  // DELETE /api/event/:id
    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/json',
      Accept: 'application/json'
    });
    this.http.delete<Events>(this.url + "/" + id)
  }

}