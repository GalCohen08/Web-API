package com.web_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web_api.model.Contact;
import com.web_api.model.Skill;
import com.web_api.repository.ContactRepository;

import javassist.NotFoundException;

@Service("ContactService")
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
	public void saveContact(Contact contact) {
		contactRepository.save(contact);
		
	}

	@Override
	public void updateContact(Contact contact) {
		contactRepository.save(contact);	
		
	}

	@Override
	public Page<Contact> getAllContact(Pageable pageable) {
		return contactRepository.findAll(pageable);
	} 
	
	@Override
	public Contact getContactByEmail(String email) {
		List<Contact> contactlList = contactRepository.findAll();
		for(Contact contact : contactlList){
			if(email.compareTo(contact.getEmail()) ==0){
				return contact;
			}
		}
		return null;
	}
	
	@Override
	public Contact getContactByPhone(String phone) {
		List<Contact> contactlList = contactRepository.findAll();
		for(Contact contact : contactlList){
			if(phone.compareTo(contact.getMobileNumber()) ==0){
				return contact;
			}
		}
		return null;
	}
	


}
