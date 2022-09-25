package com.gaurav.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.entities.Course;
import com.gaurav.model.ApiResponse;

@RestController
@RequestMapping("/user-repo")
public class UserRepoSecurityController {

	@RequestMapping(value = "/course", method = RequestMethod.GET)
	public ResponseEntity<?> getCourse(){
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "course retrieved"));
	}
	
	@RequestMapping(value = "/course", method = RequestMethod.POST)
	public ResponseEntity<?> addCourse(@RequestBody Course course){

		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "course added"));
	}
	
	
}
