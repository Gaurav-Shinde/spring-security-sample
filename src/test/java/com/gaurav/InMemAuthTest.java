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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.entities.Course;

@AutoConfigureMockMvc
@SpringBootTest
class InMemAuthTest {

	private String customerToken = new String("Basic "+Base64.getEncoder().encodeToString("customer:password".getBytes()));
	private String adminToken = new String("Basic "+Base64.getEncoder().encodeToString("admin:password".getBytes()));
	
	@Autowired
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
	   mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
//	@WithUserDetails(value = "customer") //-> requires UserDetailsServiceImpl
	public void getCourseWithStudent() throws Exception {
		mockMvc.perform(get("/in-mem/course")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("Authorization", customerToken)
						)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is(200)));
	}
	
	//post course
	@Test
	public void postCourseWithAdmin() throws Exception{
		mockMvc.perform(post("/in-mem/course")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(new Course(1,"intro to python","andrew")))
				.header("Authorization", adminToken))
		.andExpect(status().is(200))
		.andExpect(jsonPath("$.message",is("course added")) );
	}
	
	
	

}
