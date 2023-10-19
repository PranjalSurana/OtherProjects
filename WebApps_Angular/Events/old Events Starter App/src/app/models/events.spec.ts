import { eventDetails } from './eventDetails';
import { Events } from './events';

describe('Events', () => {
  it('should create an instance', () => {
    expect(new Events(new eventDetails(1, "another event", "something even cooler", "Also Made For You", "2021-02-01", "02/01/2021", 0, 0))).toBeTruthy();
  });
});
