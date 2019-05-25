package com.dharmendra.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dharmendra.controller.service.EncryptionService;
import com.dharmendra.model.Token;
import com.dharmendra.repository.TokenRepository;

@RestController
@RequestMapping(value="/api")
public class EncryptionController {

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private EncryptionService encryptionService;
	
	@GetMapping(value = "/tokens")
    @PreAuthorize("hasRole('USER')")
	public List<Token> getAllTokens() {
		return tokenRepository.findAll();
	}
	
	@GetMapping(value = "/tokens/{id}")
    @PreAuthorize("hasRole('USER')")
	public Token getToken(long id) {
		return tokenRepository.findById(id).orElseGet(null);
	}
	
	@PostMapping(value = "/tokens")
    @PreAuthorize("hasRole('USER')")
	public Token getToken(@RequestBody Token token) {
		System.out.println("Received Token"+ token.toString());
		Token savedToken = encryptionService.saveToke(token);
		System.out.println("Returned Token:: "+ token);
		return savedToken;
	}
}
