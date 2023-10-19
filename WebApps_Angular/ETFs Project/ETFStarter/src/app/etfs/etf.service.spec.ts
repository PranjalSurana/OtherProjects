import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed, fakeAsync, inject, tick } from '@angular/core/testing';

import { EtfService } from './etf.service';
import { Etfs } from '../models/etfs';

describe('EtfService', () => {

  let httpTestingController: HttpTestingController;
  let service: EtfService;

  const mockEtfs = [
    {
      "Ticker": "SGOL",
      "Fund_Name": "Aberdeen Standard Physical Gold Shares ETF",
      "Issuer": "Aberdeen Standard Investments",
      "AUM_bil": 1.83,
      "Expense_Ratio": 0.0017,
      "ThreeMoTR": 0.0796,
      "Segment": "Commodities: Precious Metals Gold"
      },
      {
      "Ticker": "SLV",
      "Fund_Name": "iShares Silver Trust",
      "Issuer": "Blackrock",
      "AUM_bil": 6.09,
      "Expense_Ratio": 0.005,
      "ThreeMoTR": -0.163,
      "Segment": "Commodities: Precious Metals Silver"
      },
      {
      "Ticker": "USO",
      "Fund_Name": "United States Oil Fund LP",
      "Issuer": "US Commodity Funds",
      "AUM_bil": 3.93,
      "Expense_Ratio": 0.0079,
      "ThreeMoTR": -0.7573,
      "Segment": "Commodities: Energy Crude Oil"
      }
  ]

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(EtfService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return etfs', inject([EtfService], fakeAsync((service: EtfService) => {
    let etflist: Etfs[] = [];
    service.getEtfs().subscribe(EtfService => etflist = EtfService);
    const req = httpTestingController.expectOne('http://localhost:8080/etfs?filter=');
    expect(req.request.method).toEqual('GET');
    // Cause all outstanding asynchronous events to complete before continuing
    tick();
    expect(etflist).toBeTruthy();
  })));

});