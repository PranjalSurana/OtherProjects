package com.fidelity.investmonkey.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.investmonkey.models.Client;
import com.fidelity.investmonkey.models.ClientIdentification;
import com.fidelity.investmonkey.exception.*;

class ClientServiceTest {

	private ClientService service;
	private ClientIdentification indianIdentification;
	private ClientIdentification usIdentification;
	private Client indianClient;
	private Client usClient;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		service=new ClientService();
		indianIdentification=new ClientIdentification("Aadhar","123456789");
		indianClient=new Client("jitin","dodeja","9663321676","ABC123","jd@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","jd123",indianIdentification);
		usIdentification=new ClientIdentification("SSN","123456789");
		usClient=new Client("jitin","dodeja","9663321676","xyz123","su@gmail.com",LocalDate.of(2001, 8, 27),"India","570011","jd123",usIdentification);
	}

	@AfterEach
	public void tearDown() throws Exception {
		service=null;
	}
	@Test
	void addNullClient() {
		assertThrows(NullPointerException.class,()->{
			service.addNewClient(null);
		});
	}
	@Test
	void addDuplicatelient(){
		service.addNewClient(indianClient);
		service.addNewClient(usClient);
		assertThrows(InvalidClientException.class,()->{
			service.addNewClient(indianClient);
		});
		
	}
	@Test
	void addNewClient() {
		service.addNewClient(indianClient);
		service.addNewClient(usClient);
		assertTrue(service.getClients().contains(indianClient));
		assertTrue(service.getClients().contains(usClient));
	}
	@Test
	void removeClient() {
		service.addNewClient(indianClient);
		service.removeClient(indianClient);
		assertEquals(service.numberOfClients(),0);
	}
	@Test
	void removeNonExistingClient() {
		service.addNewClient(indianClient);
		assertThrows(InvalidClientException.class,()->{
			service.removeClient(usClient);
		});
	}
	@Test
	void removeNullClient() {
		service.addNewClient(indianClient);
		assertThrows(NullPointerException.class,()->{
			service.removeClient(null);
		});
	}
	@Test
	void getClientById() {
		service.addNewClient(indianClient);
		Client receivedClient=service.getClientById("ABC123");
		assertEquals(receivedClient,indianClient);
	}
	@Test
	void getInvalidClientById() {
		service.addNewClient(indianClient);
		
		assertThrows(InvalidClientException.class,()->{
			service.getClientById("Aui123");
		});
		
	}
	@Test
	void loginClient(){
		service.addNewClient(indianClient);
		boolean login=service.loginClient("jd@gmail.com", "jd123");
		assertEquals(login,true);
	}
	@Test
	void loginClientWithWrongPassword(){
		service.addNewClient(indianClient);
		assertThrows(IllegalArgumentException.class,()->{
			service.loginClient("jd@gmail.com", "xzc123");
		});
	}
	
	@Test
	void loginWithInvalidClient() {
		service.addNewClient(indianClient);
		assertThrows(InvalidClientException.class,()->{
			service.loginClient("jda@gmail.com", "jd123");
		});
	}
	
	
}
