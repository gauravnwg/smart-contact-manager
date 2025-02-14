package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.repository.ContactRepository;
import com.scm.services.ContactServices;
@Service
public class ContactServicesImpl implements ContactServices {
	
	@Autowired
	private ContactRepository contactRepository ;
	private Logger logger = LoggerFactory.getLogger(ContactServicesImpl.class);
	

	@Override
	public Contact addContact(Contact contact) {
		 contact.setId(UUID.randomUUID().toString());
		return  this.contactRepository.save(contact);
		
	}

	@Override
	public List<Contact> getAllContact() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public Contact update(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Contact> getByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Contact> getByUser(User user) {
//		 
////		return  (List<Contact>) contactRepository.findByUser(user);
//	}

	@Override
	public List<Contact> search(String name, String email,
			String mobileNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
