import { GalwayTrails } from './galway-trails';

describe('GalwayTrails', () => {
  it('should create an instance', () => {
    expect(new GalwayTrails("Slieve Carran Brown and Yellow Trails", 4.7, 2.5, 55, "Easy walking trail", "Popular for its gorgeous wildflowers that can be seen along the trails in the spring and summertime")).toBeTruthy();
  });
});
