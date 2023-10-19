import { Component, OnInit } from '@angular/core';
import { Etfs } from '../models/etfs';
import { EtfService } from '../etfs/etf.service';

@Component({
  selector: 'app-etfs-list',
  templateUrl: './etfs-list.component.html',
  styleUrls: ['./etfs-list.component.css']
})
export class ETFsListComponent implements OnInit{

  etflist: Etfs[] = [];

  constructor(private etfService: EtfService) {}

  ngOnInit() {
    this.loadEtfs()
  }

  loadEtfs(): void {
    this.etfService.getEtfs().subscribe(etfs => {
      this.etflist = etfs;
    });
  }

  loadETFsBy3MTR(): void {
    this.etfService.getETFsBy3MTR().subscribe(etfs => {
      this.etflist = etfs;
    });
  }

  loadETFsByER(): void {
    this.etfService.getETFsByER().subscribe(etfs => {
      this.etflist = etfs;
    });
  }

  loadETFsyAUMBill(): void {
    this.etfService.getETFsyAUMBill().subscribe(etfs => {
      this.etflist = etfs;
    });
  }

}