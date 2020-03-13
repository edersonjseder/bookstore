package com.bookstore.backend.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserBilling implements Serializable {

	private static final long serialVersionUID = 10101L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String userBillingName;
	private String userBillingStreet1;
	private String userBillingStreet2;
	private String userBillingCity;
	private String userBillingState;
	private String userBillingCountry;
	private String userBillingZipcode;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private UserPayment userPayment;

	public Integer getId() {
		return id;
	}

	public String getUserBillingName() {
		return userBillingName;
	}

	public String getUserBillingStreet1() {
		return userBillingStreet1;
	}

	public String getUserBillingStreet2() {
		return userBillingStreet2;
	}

	public String getUserBillingCity() {
		return userBillingCity;
	}

	public String getUserBillingState() {
		return userBillingState;
	}

	public String getUserBillingCountry() {
		return userBillingCountry;
	}

	public String getUserBillingZipcode() {
		return userBillingZipcode;
	}

	public UserPayment getUserPayment() {
		return userPayment;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserBillingName(String userBillingName) {
		this.userBillingName = userBillingName;
	}

	public void setUserBillingStreet1(String userBillingStreet1) {
		this.userBillingStreet1 = userBillingStreet1;
	}

	public void setUserBillingStreet2(String userBillingStreet2) {
		this.userBillingStreet2 = userBillingStreet2;
	}

	public void setUserBillingCity(String userBillingCity) {
		this.userBillingCity = userBillingCity;
	}

	public void setUserBillingState(String userBillingState) {
		this.userBillingState = userBillingState;
	}

	public void setUserBillingCountry(String userBillingCountry) {
		this.userBillingCountry = userBillingCountry;
	}

	public void setUserBillingZipcode(String userBillingZipcode) {
		this.userBillingZipcode = userBillingZipcode;
	}

	public void setUserPayment(UserPayment userPayment) {
		this.userPayment = userPayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userBillingCity == null) ? 0 : userBillingCity.hashCode());
		result = prime * result + ((userBillingCountry == null) ? 0 : userBillingCountry.hashCode());
		result = prime * result + ((userBillingName == null) ? 0 : userBillingName.hashCode());
		result = prime * result + ((userBillingState == null) ? 0 : userBillingState.hashCode());
		result = prime * result + ((userBillingStreet1 == null) ? 0 : userBillingStreet1.hashCode());
		result = prime * result + ((userBillingStreet2 == null) ? 0 : userBillingStreet2.hashCode());
		result = prime * result + ((userBillingZipcode == null) ? 0 : userBillingZipcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBilling other = (UserBilling) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userBillingCity == null) {
			if (other.userBillingCity != null)
				return false;
		} else if (!userBillingCity.equals(other.userBillingCity))
			return false;
		if (userBillingCountry == null) {
			if (other.userBillingCountry != null)
				return false;
		} else if (!userBillingCountry.equals(other.userBillingCountry))
			return false;
		if (userBillingName == null) {
			if (other.userBillingName != null)
				return false;
		} else if (!userBillingName.equals(other.userBillingName))
			return false;
		if (userBillingState == null) {
			if (other.userBillingState != null)
				return false;
		} else if (!userBillingState.equals(other.userBillingState))
			return false;
		if (userBillingStreet1 == null) {
			if (other.userBillingStreet1 != null)
				return false;
		} else if (!userBillingStreet1.equals(other.userBillingStreet1))
			return false;
		if (userBillingStreet2 == null) {
			if (other.userBillingStreet2 != null)
				return false;
		} else if (!userBillingStreet2.equals(other.userBillingStreet2))
			return false;
		if (userBillingZipcode == null) {
			if (other.userBillingZipcode != null)
				return false;
		} else if (!userBillingZipcode.equals(other.userBillingZipcode))
			return false;
		return true;
	}
	
}
