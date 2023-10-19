import { Car } from './car';

describe('Car', () => {
  it('should create an instance', () => {
    expect(new Car(2, "Tesla", "Roadster", 220000, 2017)).toBeTruthy();
  });
});
