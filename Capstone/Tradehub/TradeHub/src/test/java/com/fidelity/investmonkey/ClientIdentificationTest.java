package com.fidelity.investmonkey;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.fidelity.investmonkey.models.*;
import com.fidelity.investmonkey.exception.*;

public class ClientIdentificationTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createValidClientIdentifiction() {
		ClientIdentification identification=new ClientIdentification("Aadhar","123456789");
		assertNotNull(identification);
	}
	@Test
	public void createInvalidClientIdentificationType() {
		assertThrows(InvalidIdentificationType.class,()->{
			ClientIdentification identification=new ClientIdentification("Pan","123456789");
		});
		
	}
	public void createInvalidClientIdentificationValue() {
		assertThrows(NullPointerException.class,()->{
			ClientIdentification identification=new ClientIdentification("Aadhar",null);
		});
		
	}
}
