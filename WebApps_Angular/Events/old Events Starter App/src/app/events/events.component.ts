import { Component, OnInit } from '@angular/core';
import { EventsService } from '../service/events.service';
import { Events } from '../models/events';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit{

  eventslist: Events[] = []

  constructor(
    private eventService: EventsService
  ) {}

  ngOnInit() {
    this.loadEvents();
  }

  loadEvents() {
    this.eventService.getEvents().subscribe(data => {this.eventslist = data});
  }
  // addEvents() {    // newEvent: Events
  //   this.eventService.addEvent(newEvent)
  //   .subscribe(() => this.getEvents());
  //   }

  incrementLikes(id: number): void {
    this.eventService.incrementLikes(id).subscribe(() => this.loadEvents())
  }

  incrementDislikes(id: number): void {
    this.eventService.incrementDislikes(id).subscribe(() => this.loadEvents())
  }

  editEvents(id: number) {
    this.eventService.updateEventById(id).subscribe(() => this.loadEvents())
  }

  deleteEvents(id: number) {
    this.eventService.deleteEvent(id)
  }

}
