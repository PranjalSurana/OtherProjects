import { TestBed } from '@angular/core/testing';

import { ImageLoadService } from './image-load.service';

// Can't really test this as there is no programmatic way to create a File object
describe('ImageLoadService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({});
    });

    it('should be created', () => {
        const service: ImageLoadService = TestBed.get(ImageLoadService);
        expect(service).toBeTruthy();
    });
});
