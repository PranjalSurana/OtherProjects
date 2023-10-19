import { TestBed, fakeAsync, inject, tick } from '@angular/core/testing';

import { GalwayTrailService } from './galway-trail.service';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { GalwayTrails } from '../models/galway-trails';
import { HttpErrorResponse } from '@angular/common/http';

describe('GalwayTrailService', () => {

  let httpTestingController: HttpTestingController;
  let service: GalwayTrailService;

  const mockTrails = [
    {
      "name": "Slieve Carran Brown and Yellow Trails",
      "length": 4.7,
      "timeToComplete": 2.5,
      "drivingTime": 55,
      "difficulty": "Easy walking trail",
      "comments": "Popular for its gorgeous wildflowers that can be seen along the trails in the spring and summertime"
      },
      {
      "name": "Rinville Park Loop",
      "length": 2.3,
      "timeToComplete": 1,
      "drivingTime": 0,
      "difficulty": "Easy trail",
      "comments": "It's right in Galway City and provides excellent natural views. It is filled with gorgeous wildflowers and birds"
      },
      {
      "name": "Portumna Forest Park",
      "length": 15,
      "timeToComplete": 4.4,
      "drivingTime": 57,
      "difficulty": "Moderate loop trail",
      "comments": "A breathtaking area that features an abbey and castle, both from a few hundred years ago!"
      }
  ]

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(GalwayTrailService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should return Trails', inject([GalwayTrailService], fakeAsync((service: GalwayTrailService) => {
    let trailist: GalwayTrails[] = [];
    service.getDefaultTrails('defaultAsc').subscribe(GalwayTrailService => trailist = GalwayTrailService);
    const req = httpTestingController.expectOne('http://localhost:8080/trails?sortBy=');
    expect(req.request.method).toEqual('GET');
    // Cause all outstanding asynchronous events to complete before continuing
    tick();
    expect(trailist).toBeTruthy();
  })));

  it('should handle a 404 error', inject([GalwayTrailService], fakeAsync((service: GalwayTrailService) => {
    let errorResp: HttpErrorResponse;
    let errorReply: string = '';
    const errorHandlerSpy = spyOn(service,'handleError').and.callThrough();
    service.getDefaultTrails('defaultAsc').subscribe({next: () => fail('Should not succeed'), error: (err) => errorReply = err});
    const req = httpTestingController.expectOne(service.url);
    expect(req.request.method).toEqual('GET');
    req.flush('Forced 404', {
      status: 404,
      statusText: 'Not Found'
    });
    tick();
    expect(errorReply).toBe('Unable to contact service; please try again later.');
    expect(errorHandlerSpy).toHaveBeenCalled();
    errorResp = errorHandlerSpy.calls.argsFor(0)[0];
    expect(errorResp.status).toBe(404);
  })));

});