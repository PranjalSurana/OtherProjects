package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Executable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import main.CrewMember;
import main.FreightShip;
import main.Officer;
import main.Sailor;

class FreightShipTest {

	private FreightShip frieghtShip;
	private List<CrewMember> crewTest;
	
	@BeforeEach
	void setUp() {
		crewTest = new ArrayList<>();
		crewTest.add(new Sailor(1, "sailor", 10, "Engineer", new BigDecimal("100"), 100));
		crewTest.add(new Officer(2, "officer", 10, "DGP", new BigDecimal("10000")));
		frieghtShip = new FreightShip(3, crewTest);
	}
	
//	@AfterEach
//	void tearDown() throws Exception {
//		frieghtShipTest = null;
//	}

	@Test
	@DisplayName("addCrewMember Test: Member Added Successfully")
	public void addCrewMemberTest_Success() {
		CrewMember mockSailor = new Sailor(2, "sssailor", 10, "Engineer", new BigDecimal("100"), 100);
		frieghtShip.addCrewMember(mockSailor);
		assertEquals(3, crewTest.size());
	}
	
	@Test
	@DisplayName("addCrewMember Test: Member Added Successfully")
	public void addCrewMemberTest_Failure_NullCrew() {
		assertThrows(IllegalStateException.class, () -> {
			frieghtShip.addCrewMember(null);
		});
	}

}