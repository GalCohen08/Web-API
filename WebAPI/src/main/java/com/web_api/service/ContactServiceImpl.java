package com.web_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web_api.model.Contact;
import com.web_api.repository.ContactRepository;

import javassist.NotFoundException;

@Service("employeeService")
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	ContactRepository contactRepository;

	@Override
	public Contact getContactById(long id) {
		try {
			return contactRepository.findById(id).orElseThrow(()-> new NotFoundException("Unable to get Contact with id =  " + id));
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteContactByid(long id) {
		contactRepository.deleteById(id);
		
	}

	@Override
	public void saveContact(Contact employee) {
		contactRepository.save(employee);
		
	}

	@Override
	public void updateContact(Contact employee) {
		contactRepository.save(employee);	
		
	}

	@Override
	public Page<Contact> getAllContact(Pageable pageable) {
		return contactRepository.findAll(pageable);
	} 
	


}
