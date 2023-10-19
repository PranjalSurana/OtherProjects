import { Component, EventEmitter, OnInit } from '@angular/core';
import { GalwayTrails } from '../models/galway-trails';
import { GalwayTrailService } from '../service/galway-trail.service';

@Component({
  selector: 'app-galwaytrails',
  templateUrl: './galwaytrails.component.html',
  styleUrls: ['./galwaytrails.component.css']
})
export class GalwaytrailsComponent implements OnInit {

  selectedValue: string = ''

  galwaytraillist: GalwayTrails[] = []

  constructor(private galwayTrailService: GalwayTrailService) {}

  ngOnInit() {
    this.galwayTrailService.getDefaultTrails(this.selectedValue).subscribe(trails => {
      this.galwaytraillist = trails;
    });
  }

  loadDefaultTrails(event?: Event): void {
    this.selectedValue = (event?.target as HTMLSelectElement).value;
    this.galwayTrailService.getDefaultTrails(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }

  loadSortedByName(event: Event): void {
    this.selectedValue = (event.target as HTMLSelectElement).value;
    this.galwayTrailService.getTrailsSortedByName(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }

  loadSortedByLength(event: Event): void {
    this.selectedValue = (event.target as HTMLSelectElement).value;
    this.galwayTrailService.getTrailsSortedByLength(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }

  loadTrailsSortedByTimeToComplete(event: Event): void {
    this.selectedValue = (event.target as HTMLSelectElement).value;
    this.galwayTrailService.getTrailsSortedByTimeToComplete(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }

  loadTrailsSortedByDrivingTime(event: Event): void {
    this.selectedValue = (event.target as HTMLSelectElement).value;
    this.galwayTrailService.getTrailsSortedByDrivingTime(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }

  loadTrailsSortedByDifficulty(event: Event): void {
    this.selectedValue = (event.target as HTMLSelectElement).value;
    this.galwayTrailService.getTrailsSortedByDifficulty(this.selectedValue).subscribe(trails => {
        this.galwaytraillist = trails;
      });
  }
}