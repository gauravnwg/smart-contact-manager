package com.scm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;
@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {
	
	Optional<Contact> findByEmail(String email);
	
//	List findByUser(User user);
	
}
