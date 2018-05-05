package com.web_api.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
	@Id 
	@GeneratedValue
	@Column(name = "contact_id")
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String fullName;
	
	private String address;
	
	private String eMaill;
	
	private String  mobileNumber;
	
	public Contact() {
		super();
	}
	
	public Contact(String name, String lastName, String address, String eMaill, String  mobileNumbere) {
		super();
		this.firstName = name;
		this.lastName = lastName;
		this.fullName = firstName+" "+lastName;
		this.address = address;
		this.eMaill = eMaill;
		this.mobileNumber = mobileNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String geteMaill() {
		return eMaill;
	}

	public void seteMaill(String eMaill) {
		this.eMaill = eMaill;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName() {
		this.fullName = firstName+" "+lastName;
	}
	
}
