package com.web_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web_api.model.Contact;

public interface ContactService {
	
	Contact getContactById(long id);
	
	void deleteContactByid(long id);
	
	Page<Contact> getAllContact(Pageable pageable);
	
	void saveContact(Contact employee);
	
	void updateContact(Contact employee);
}
