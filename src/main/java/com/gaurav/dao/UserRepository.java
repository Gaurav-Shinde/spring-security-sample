package com.gaurav.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaurav.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String user);
}
