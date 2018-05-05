package com.web_api.service;

import java.util.List;

import com.web_api.model.Contact;

public interface ContactService {
	
	Contact getEmployeeById(long id);
	
	void deleteEmployeeByid(long id);
	
	List<Contact> getAllEmployees();
	
	void saveUser(Contact employee);
	
	void updateEmplyee(Contact employee);
}
