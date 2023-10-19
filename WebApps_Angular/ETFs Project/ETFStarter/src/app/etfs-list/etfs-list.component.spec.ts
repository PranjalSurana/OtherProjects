import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ETFsListComponent } from './etfs-list.component';
import { of } from 'rxjs';
import { EtfService } from '../etfs/etf.service';

describe('ETFsListComponent', () => {
  let component: ETFsListComponent;
  let fixture: ComponentFixture<ETFsListComponent>;

  let mockEtfsService = jasmine.createSpyObj('EtfService', ['getEtfs']);

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
  
  mockEtfsService.getEtfs.and.returnValue(of(mockEtfs));

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ETFsListComponent ],
      providers: [
        { 
          provide: EtfService, useValue: mockEtfsService
        }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ETFsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve books from the service', () => {
    expect(component.etflist.length).toBeGreaterThanOrEqual(3);
   }); 

  it('should expect for make of 1st Ticker to be SGOL', () => {    
    expect(component.etflist[0].Ticker).toBe("SGOL")
  })

  it('should expect for model of 3rd AUM_bil to be 3.93', () => {    
    expect(component.etflist[2].AUM_bil).toBe(3.93)
  })

});