import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PhysicalFilm extends Film {

	private int numberOfReels;
	private BigDecimal lengthOfReelInHours;

	public PhysicalFilm(String filmId, String filmTitle, LocalDateTime timeStamp, int numberOfReels, BigDecimal lengthOfReelInHours) {
		super(filmId, filmTitle, timeStamp);
		this.numberOfReels = numberOfReels;
		this.lengthOfReelInHours = lengthOfReelInHours;
	}
	
	@Override
	public BigDecimal runningTime() {
		return lengthOfReelInHours.multiply(new BigDecimal(numberOfReels)).setScale(2);
	}

	@Override
	public int capacityOfFilm() {
		return numberOfReels;
	}
	
}