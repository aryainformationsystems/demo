package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehicle")
public class Vehicle extends GeneralEntity {
	@Id
	@NotBlank(message = "Registration of the vehicle is required")
	private String registration;
	
	@NotBlank(message = "Owner name of vehicle is required")
	private String owner;
	
	@NotBlank(message = "Engine number of vehicle is required")
	private String engineNumber;
	
	@NotBlank(message = "Chassis number of vehicle is required")
	private String chassisNumber;
	
	@NotNull(message = "Registration date of vehicle is required")
	private Date dateOfRegistration;
	
	@NotNull(message = "Expiration date of vehicle is required")
	private Date dateOfExpiry;
	
	@NotBlank(message = "Address of vehicle owner is required")
	private String address;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
