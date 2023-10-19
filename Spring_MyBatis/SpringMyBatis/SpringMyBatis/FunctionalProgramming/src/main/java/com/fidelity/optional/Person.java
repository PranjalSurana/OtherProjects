package com.fidelity.optional;

import java.util.Optional;
import java.util.Random;

public class Person {
	public static final Person GUEST = new Person("guest", "", null);

	private String firstName; // required (not null)
	private String lastName; // required (not null)
	private String phoneNumber; // optional (may be null)

	// constructor ensures non-null fields really are non-null
	// optional field can just be stored directly, as null means optional
	public Person(String firstname, String lastname, String phone) {
		this.firstName = Preconditions.checkNotNull(firstname);
		this.lastName = Preconditions.checkNotNull(lastname);
		this.phoneNumber = phone;
	}

	// TODO: complete the definition of the special getter 
	// for optional phoneNumber field
	public Optional<String> getPhoneNumber() {
		return Optional.ofNullable("");
	}

	// return optional instead of null
	// for business logic methods that may not find a result
	public static Optional<Person> findPerson(String phoneNumber) {
		Optional<Person> person = null;
		boolean found = new Random().nextBoolean();

		if (found) {
			// TODO: return Person.GUEST if found
			
		} else {
			// TODO: return Optional.empty() if not found
			
		}

		return person;
	}

	// normal getters
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
