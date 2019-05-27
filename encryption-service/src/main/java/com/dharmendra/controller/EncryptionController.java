package com.dharmendra.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dharmendra.exception.ResourceNotFoundException;
import com.dharmendra.model.Token;
import com.dharmendra.payload.TokenPayload;
import com.dharmendra.repository.TokenRepository;
import com.dharmendra.repository.UserRepository;
import com.dharmendra.service.EncryptionService;

@RestController
@RequestMapping(value="/api")
public class EncryptionController {

	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private EncryptionService encryptionService;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/tokens")
    @PreAuthorize("hasRole('USER')")
	public List<Token> getAllTokens() {
		return tokenRepository.findAll();
	}
	 
	@GetMapping(value = "/tokens/createdBy/{username}")
    @PreAuthorize("hasRole('USER')")
	public List<Token> getAllTokensCreatedBy(@PathVariable String username) {
		return tokenRepository.findAllByCreatedByUsername(username);
	}
	
	
	@GetMapping(value = "/tokens/{id}")
    @PreAuthorize("hasRole('USER')")
	public Token getToken(@PathVariable long id) {
		return tokenRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Token", "id", Long.valueOf(id) ));
	}
	
	@PostMapping(value = "/tokens")
    @PreAuthorize("hasRole('USER')")
	public Token saveToken(@Valid @RequestBody TokenPayload token) {
		return encryptionService.saveToke(token);
	}
}
