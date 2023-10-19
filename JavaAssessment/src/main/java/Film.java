import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Film {
	
	private String filmId;
	private String filmTitle;
	private LocalDateTime timeStamp;
	
	public Film(String filmId, String filmTitle, LocalDateTime timeStamp) {
		this.filmId = filmId;
		this.filmTitle = filmTitle;
		this.timeStamp = timeStamp;
	}
	
	public abstract BigDecimal runningTime();
	
	public abstract int capacityOfFilm();

	public String getFilmId() {
		return filmId;
	}

}