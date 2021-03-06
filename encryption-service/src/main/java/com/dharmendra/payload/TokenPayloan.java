package com.dharmendra.payload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class TokenPayloan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty(message = "originalToken must be non be not empty")
	private @Column(unique = true) String originalToken;
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
		TokenPayloan other = (TokenPayloan) obj;
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
