package com.roifmr.contactapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roifmr.contactapi.model.Contact;

/**
 * ContactController is a simple RESTful API that serves contact info.
 * It is provided as a backend for a Node-based API in an exercise 
 * that involves implementing a mid-tier API.
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {
	private static final List<Contact> initialContacts = List.of(
		new Contact(
			1, 
			"Ada", 
			"Lovelace", 
			"Ms.",
			"Dev Inc.", 
			"Developer", 
			"+359777123456", 
			List.of("+359777456789", "+359777112233"),
			"adal@computeengine.com", 
			List.of("adal@firstprogrammer.com"),
			List.of("Dev", "Family")
		),
		new Contact(
			2, 
			"Grace", 
			"Hopper", 
			"Adm.",
			"Fidelity", 
			"Manager", 
			"+359777223344",
			List.of(), 
			"admiralghopper@usn.gov", 
			List.of("admgrace@usn.gov"), 
			List.of("Dev")
		)
	);

	private List<Contact> contacts = new ArrayList<>(initialContacts);
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Contact> getAllContacts() {
		return contacts;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Contact> getContact(@PathVariable int id) {
		List<Contact> match = contacts.stream()
									  .filter(c -> c.getId() == id)
									  .toList();
		return match.size() > 0 ? 
				ResponseEntity.ok(match.get(0)) : 
				ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public RowCountDto addContact(@RequestBody Contact contact) {
		contacts.add(contact);
		return new RowCountDto(1);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public RowCountDto updateContact(@RequestBody Contact contact) {
		int rowCount = getContact(contact.getId()) != null ? 1 : 0;
		if (rowCount > 0) {
			contacts = new ArrayList<>(
						contacts.stream()
							    .filter(c -> c.getId() != contact.getId())
							    .toList());
			contacts.add(contact);
		}
		return new RowCountDto(rowCount);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RowCountDto deleteContact(@PathVariable int id) {
		int rowCount = getContact(id) != null ? 1 : 0;
		if (rowCount > 0) {
			contacts = new ArrayList<>(
						contacts.stream()
							    .filter(c -> c.getId() != id)
							    .toList());
		}
		return new RowCountDto(rowCount);
	}
	
	@RequestMapping(value = "/shutdown", 
					method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void shutdown() {
		reset();
	}
	
	@RequestMapping(value = "/restart", 
					method = RequestMethod.OPTIONS)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void reset() {
		contacts = new ArrayList<>(initialContacts);
	}
}
