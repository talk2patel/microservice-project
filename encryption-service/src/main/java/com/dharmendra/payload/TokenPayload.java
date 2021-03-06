package com.dharmendra.payload;

import javax.validation.constraints.NotEmpty;

public class TokenPayload {

	private long id;
	@NotEmpty(message="originalToken must be non be not empty")
	private String originalToken;
	private String encryptedToken;
	private String createdBy;
	
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Override
	public String toString() {
		return "Token [id=" + id + ", originalToken=" + originalToken + ", encryptedToken=" + encryptedToken + "]";
	}
	
}
