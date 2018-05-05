package com.web_api.service;

import java.util.List;

import com.web_api.model.Contact;

public interface ContactService {
	
	Contact getContactById(long id);
	
	void deleteContactByid(long id);
	
	List<Contact> getAllContact();
	
	void saveUser(Contact employee);
	
	void updateContact(Contact employee);
}
