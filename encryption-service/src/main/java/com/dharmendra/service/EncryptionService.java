package com.dharmendra.service;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dharmendra.exception.EncryptionException;
import com.dharmendra.model.Token;
import com.dharmendra.model.User;
import com.dharmendra.payload.TokenPayload;
import com.dharmendra.repository.TokenRepository;
import com.dharmendra.repository.UserRepository;

@Service
public class EncryptionService {

	private static Cipher cipher;
	private String strKey="ABCDSS";
	private static final Logger LOG = LogManager.getLogger(EncryptionService.class);
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() {
		try {
			cipher = Cipher.getInstance("Blowfish");
			SecretKeySpec skeyspec = new SecretKeySpec(strKey.getBytes(), "Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
		} catch (Exception e) {
			LOG.error("Exception while initializing EncryptionService", e);
			System.exit(-1);
		}
	}

	public Token saveToke(TokenPayload tokenPayload) {
		Optional<User> user = userRepository.findByUsername(tokenPayload.getCreatedBy());
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("No user found with username"+tokenPayload.getCreatedBy());
		}
		Token token = new Token();
		token.setOriginalToken(tokenPayload.getOriginalToken());
		token.setCreatedBy(user.get());
		token.setEncryptedToken(encrypt(token.getOriginalToken()));
		tokenRepository.save(token);
		return token;
	}
	public static String encrypt(String plainText)  {
		String encryptedText = null;
		try {
			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			encryptedText = new String(encrypted);
		} catch (Exception e) {
			throw new EncryptionException("Error while encrypting " + plainText, e);
		}
		return encryptedText;
	}
}
