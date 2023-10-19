import { Etfs } from './etfs';

describe('Etfs', () => {
  it('should create an instance', () => {
    expect(new Etfs("BAR", "GraniteShares Gold Trust", "GraniteShares", 0.877, 0.0017, 0.0795, "Commodities: Precious Metals Gold")).toBeTruthy();
  });
});