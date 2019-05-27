package com.dharmendra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.NonNull;

@Entity
public class Token {

	@Id 
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private @NonNull  @Column(unique=true)String originalToken;
	private @NonNull String encryptedToken;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOriginalToken() {
		return originalToken;
	}
	public void setOriginalToken(String originalToken) {
		this.originalToken = originalToken;
	}
	public String getEncryptedToken() {
		return encryptedToken;
	}
	public void setEncryptedToken(String encryptedToken) {
		this.encryptedToken = encryptedToken;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((originalToken == null) ? 0 : originalToken.hashCode());
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
		Token other = (Token) obj;
		if (originalToken == null) {
			if (other.originalToken != null)
				return false;
		} else if (!originalToken.equals(other.originalToken))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Token [id=" + id + ", originalToken=" + originalToken + ", encryptedToken=" + encryptedToken + "]";
	}
	
}
