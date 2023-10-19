import java.util.List;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FilmArchive {

	private int maxStorageCapacity;
	private List<Film> film;
	private int currentCapacity;
	
	public FilmArchive(int maxStorageCapacity, List<Film> film) {
		if(maxStorageCapacity < 0)
			throw new IllegalArgumentException("Enter a valid Maximum Storage Capacity!");
		if(maxStorageCapacity == 0)
			throw new IllegalArgumentException("Maximum Storage Capacity can't be zero!");
		this.maxStorageCapacity = maxStorageCapacity;
		this.film = film;
		this.currentCapacity = maxStorageCapacity;
	}
	
	public List<Film> retrieveAllFilms() {
		return film;
	}
	
	public Film retrieveFilmById(String filmId) {
		if(filmId == "" || filmId == null) {
			throw new IllegalArgumentException("Invalid Film ID");
		}
		for (Film f : film) {
			if (filmId.equals(f.getFilmId())) {
				return f;
			}
		}
		return null;
	}
	
	public void addNewFilm(Film newFilm) {
		if(newFilm == null)
			throw new IllegalArgumentException("Film cannot be null");
		if(remainingStorageCapacity() == 0)
			throw new IllegalStateException("Film Archive Capacity is Full!");
		if(retrieveFilmById(newFilm.getFilmId()) != null)
			throw new IllegalStateException("Film with Id " + newFilm.getFilmId() + " already exists!");
		if(newFilm.capacityOfFilm() > remainingStorageCapacity())
			throw new IllegalStateException("Capacity of Given Film is More than Remaining Storage Capacity");
		film.add(newFilm);
		currentCapacity = remainingStorageCapacity();
	}
	
	public void deleteFilm(String filmId) {
		if(filmId == null || filmId == "") {
			throw new IllegalArgumentException("Invalid Film ID");
		}
		for (Film f : film) {
			if (filmId.equals(f.getFilmId())) {
				currentCapacity = getCurrentCapacity() + f.capacityOfFilm(); 
				film.remove(f);
				return;
			}
		}
		throw new IllegalStateException("Film Archive does not contain Film with ID " + filmId);
	}
	
	public int initialStorageCapacity() {
		return maxStorageCapacity;
	}
	
	public int numberOfFilmsInArchive() {
		return film.size();
	}
	
	public int remainingStorageCapacity() {
		int numberOfPhysicalFilmCapacity = 0;
		for(Film f: film) {
			numberOfPhysicalFilmCapacity += f.capacityOfFilm();
		}
		return maxStorageCapacity - numberOfPhysicalFilmCapacity;
	}
	
	public BigDecimal totalRunningTime() {
		BigDecimal numberOfHours = BigDecimal.ZERO;
		for(Film f: film) {			
			numberOfHours = numberOfHours.add(f.runningTime());// get total running time
		}
		return numberOfHours.setScale(2, RoundingMode.HALF_EVEN);
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}
	
}