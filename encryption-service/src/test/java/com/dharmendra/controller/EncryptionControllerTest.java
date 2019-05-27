package com.dharmendra.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.dharmendra.model.Token;
import com.dharmendra.payload.TokenPayload;
import com.dharmendra.service.EncryptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EncryptionControllerTest {

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private EncryptionService encryptionService;

	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@WithMockUser(value = "spring")
	@Test
	@DirtiesContext
	public void getTokenShouldSucceedWith200() throws Exception {
		insertToken();
		
		MvcResult result = mvc.perform(get("/api/tokens/").contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk()).andReturn();
	}
	
	@WithMockUser(value = "spring")
	@Test
	@DirtiesContext
	public void whenGetTokensCalledWithValidTokenIdWhouldReturnToken() throws Exception {
		Token token = insertToken();
		mvc.perform(get("/api/tokens/"+token.getId()).contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.originalToken").value(token.getOriginalToken()));
	}
	

	@WithMockUser(value = "spring")
	@Test
	public void getThrowExceptionWhenGetTokensCalledWithInvalidTokenId() throws Exception {
		mvc.perform(get("/api/tokens/123").contentType(MediaType.APPLICATION_JSON)).andDo(print())
		.andExpect(status().isNotFound());
	}

	@WithMockUser(value = "spring")
	@Test
	@DirtiesContext
	public void saveTokenShouldSucceedWith200() throws Exception {
		Token token = new Token();
		token.setOriginalToken("Abcdef");
		mvc.perform( MockMvcRequestBuilders
			      .post("/api/tokens/")
			      .content(asJsonString(token))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}
	
	@WithMockUser(value = "spring")
	@Test
	public void saveTokenShouldFailWhenOriginalTokenPropertyIsmissingInRequest() throws Exception {
		TokenPayload token = new TokenPayload();
		mvc.perform( MockMvcRequestBuilders
			      .post("/api/tokens/")
			      .content(asJsonString(token))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isBadRequest());
	}
	
	public String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  
	
	private Token insertToken() {
		Token token = new Token();
		token.setOriginalToken("Abcdef");
		return encryptionService.saveToke(token);
	}
}
