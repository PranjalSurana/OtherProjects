package com.fidelity.investmonkey;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fidelity.investmonkey.models.*;

class ClientTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void createClient() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		Client client=new Client("jitin","dodeja","9663321676","ABC123","jd@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","jd123",identification);
	}
	@Test
	void createInvalidClient() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		assertThrows(NullPointerException.class,()->{
			Client client=new Client("jitin","dodeja","9663321676",null,"jd@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","jd123",identification);
		});
	}
	@Test
	void underAgeClient() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		assertThrows(IllegalArgumentException.class,()->{
			Client client=new Client("jitin","dodeja","9663321676","JD123","jd@gmail.com",LocalDate.of(2010, 8, 27),"India","570011","jd123",identification);
		});
	}
	@Test
	void InvalidMailClient() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		assertThrows(IllegalArgumentException.class,()->{
			Client client=new Client("jitin","dodeja","9663321676","JD123","jdgmail.com",LocalDate.of(2010, 8, 27),"India","570011","jd123",identification);
		});
	}

}
