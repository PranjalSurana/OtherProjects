import { Component, OnInit } from '@angular/core';
import { WarehouseService } from '../warehouse-service/warehouse.service'
import { Widget } from "../models/widget";

@Component({
  selector: 'app-widget-page',
  templateUrl: './widget-page.component.html',
  styleUrls: ['./widget-page.component.css']
})
export class WidgetPageComponent implements OnInit {
  private widgetsUrl: string = 'http://localhost:8081/resource-server/warehouse/widgets';
  public widgets: Widget[] = [];
  public error: string = '';

  constructor(private service: WarehouseService) { }

  getWidgets() {
    this.service.getWidgets()
        .subscribe(
          data => this.widgets = data,
          error => this.error = 'Error');
    }

  ngOnInit(): void {
  }
}
