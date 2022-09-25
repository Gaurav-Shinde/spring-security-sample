package com.gaurav;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.dao.UserRepository;
import com.gaurav.entities.Course;
import com.gaurav.entities.User;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
class UserRepoAuthTest {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
	   mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	   userRepository.save(new User(1,"customer","password","CUSTOMER"));
	   userRepository.save(new User(2,"admin","password","ADMIN"));
	}
	
	@Test
//	@WithUserDetails(value = "customer") //-> requires UserDetailsService and also a transaction since executes before @BeforeEach
	public void getCourseWithStudent() throws Exception {
		mockMvc.perform(get("/user-repo/course")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", getBasicAuthToken("customer:password")))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is(200)));
	}
	
//	//post course
//	@Test
//	public void postCourseWithAdmin() throws Exception{
//		mockMvc.perform(post("/in-mem/course")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.accept(MediaType.APPLICATION_JSON_VALUE)
//				.content(objectMapper.writeValueAsString(new Course(1,"intro to python","andrew")))
//				.header("Authorization", adminToken))
//		.andExpect(status().is(200))
//		.andExpect(jsonPath("$.message",is("course added")) );
//	}
	
	
	String getBasicAuthToken(String pair) {
		return new String("Basic "+Base64.getEncoder().encodeToString(pair.getBytes()));
	}

}
