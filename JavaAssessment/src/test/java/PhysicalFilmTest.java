import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhysicalFilmTest {

	PhysicalFilm physicalFilm;
	
	@Test
	@DisplayName("digitalFimCreation Test: Successfully Created")
	void digitalFimCreationTest_Success() {
		physicalFilm = new PhysicalFilm("2", "physicalFilm1", LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30), 5, new BigDecimal("20"));
		assertNotNull(physicalFilm);
	}
	
	@Test
	@DisplayName("calculateRunningTime Test: Success")
	void calculateRunningTimeTest_Success() {
		physicalFilm = new PhysicalFilm("2", "physicalFilm1", LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30), 5, new BigDecimal("20"));
		assertEquals(new BigDecimal("100.00"), physicalFilm.runningTime());
	}
	
	@Test
	@DisplayName("calculateCapacity Test: Success")
	void calculateCapacityTest_Success() {
		physicalFilm = new PhysicalFilm("2", "physicalFilm1", LocalDateTime.of(2014, Month.JANUARY, 1, 10, 10, 30), 5, new BigDecimal("20"));
		assertEquals(5, physicalFilm.capacityOfFilm());
	}

}