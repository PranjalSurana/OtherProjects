package com.roifmr.contactapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Contact {
	private int id;
	private String firstname;
	private String lastname;
	private String title;
	private String company;
	private String jobtitle;
	private String primarycontactnumber;
	private List<String> othercontactnumbers;
	private String primaryemailaddress;
	private List<String> emailaddresses;
	private List<String> groups;
	
	public Contact() {}

	public Contact(int id, String firstname, String lastname, String title, String company, String jobtitle,
			String primarycontactnumber, List<String> othercontactnumbers, String primaryemailaddress,
			List<String> emailaddresses, List<String> groups) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.title = title;
		this.company = company;
		this.jobtitle = jobtitle;
		this.primarycontactnumber = primarycontactnumber;
		this.othercontactnumbers = new ArrayList<>(othercontactnumbers);
		this.primaryemailaddress = primaryemailaddress;
		this.emailaddresses = new ArrayList<>(emailaddresses);
		this.groups = new ArrayList<>(groups);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getPrimarycontactnumber() {
		return primarycontactnumber;
	}

	public void setPrimarycontactnumber(String primarycontactnumber) {
		this.primarycontactnumber = primarycontactnumber;
	}

	public List<String> getOthercontactnumbers() {
		return othercontactnumbers;
	}

	public void setOthercontactnumbers(List<String> othercontactnumbers) {
		this.othercontactnumbers = othercontactnumbers;
	}

	public String getPrimaryemailaddress() {
		return primaryemailaddress;
	}

	public void setPrimaryemailaddress(String primaryemailaddress) {
		this.primaryemailaddress = primaryemailaddress;
	}

	public List<String> getEmailaddresses() {
		return emailaddresses;
	}

	public void setEmailaddresses(List<String> emailaddresses) {
		this.emailaddresses = emailaddresses;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, emailaddresses, firstname, groups, id, jobtitle, lastname, othercontactnumbers,
				primarycontactnumber, primaryemailaddress, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		return Objects.equals(company, other.company) && Objects.equals(emailaddresses, other.emailaddresses)
				&& Objects.equals(firstname, other.firstname) && Objects.equals(groups, other.groups) && id == other.id
				&& Objects.equals(jobtitle, other.jobtitle) && Objects.equals(lastname, other.lastname)
				&& Objects.equals(othercontactnumbers, other.othercontactnumbers)
				&& Objects.equals(primarycontactnumber, other.primarycontactnumber)
				&& Objects.equals(primaryemailaddress, other.primaryemailaddress) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", title=" + title
				+ ", company=" + company + ", jobtitle=" + jobtitle + ", primarycontactnumber=" + primarycontactnumber
				+ ", othercontactnumbers=" + othercontactnumbers + ", primaryemailaddress=" + primaryemailaddress
				+ ", emailaddresses=" + emailaddresses + ", groups=" + groups + "]";
	}
	
	
}
