package com.dharmendra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dharmendra.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{

	List<Token> findAllByCreatedByUsername(String userName);
}
