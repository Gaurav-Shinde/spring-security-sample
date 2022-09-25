package com.gaurav.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
//@Table(name = "Users")
public class User {

	@Id
	private int id;
	private String username;
	private String password;
	private String role;
}
