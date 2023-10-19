package com.fidelity.investmonkey.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	private String firstName;
	private String lastName;
	private String phone;
	private String clientId;
	private String email;
	private LocalDate dateOfBirth;
	private String country;
	private String postalCode;
	private String password;
	private ClientIdentification clientIdentification;
	public Client(String firstName,String lastName, String phone, String clientId, String email, LocalDate dateOfBirth, String country,
			String postalCode, String password, ClientIdentification clientIdentification) {
		super();
		if(firstName==null||lastName==null||phone==null||clientId==null||email==null||dateOfBirth==null||country==null||country==null||postalCode==null||password==null) {
			throw new NullPointerException("All fields are mandatory");
		}
		if(ChronoUnit.YEARS.between(dateOfBirth,LocalDate.now())<18) {
			throw new IllegalArgumentException("Age should be above 18 to continue investing");
		}
		if(!verifyEmail(email)) {
			throw new IllegalArgumentException("Incorrect Emailo Address");
		}
		this.firstName=firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.clientId = clientId;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.country = country;
		this.postalCode = postalCode;
		this.password = password;
		this.clientIdentification = clientIdentification;
	}
	public boolean verifyEmail(String email) {
		Pattern pattern= Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhone() {
		return phone;
	}
	public String getClientId() {
		return clientId;
	}
	public String getEmail() {
		return email;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public String getCountry() {
		return country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getPassword() {
		return password;
	}
	public ClientIdentification getClientIdentification() {
		return clientIdentification;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setClientIdentification(ClientIdentification clientIdentification) {
		this.clientIdentification = clientIdentification;
	}
	
	
	
	
	
}
