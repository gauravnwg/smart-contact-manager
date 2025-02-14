package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactServices {
	// add contact
     Contact addContact(Contact contact);
     // contact update
     Contact update(Contact contact);
     // contact delete
     void delete(String id);
     //get all contact
     List<Contact> getAllContact();
     
     // get contact by userId
      List<Contact> getByUserId(String userId);
      
     // get by user 
      
      List<Contact> getByUser(User user);
      
      // search contact
        
     List<Contact> search(String name , String email , String mobileNumber);
      
       
}

