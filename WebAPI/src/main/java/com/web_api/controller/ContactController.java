package com.web_api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.web_api.model.Contact;
import com.web_api.repository.ContactRepository;
import com.web_api.service.ContactService;
import com.web_api.utils.CustomErrorType;

@RestController
@RequestMapping("/api") 
public class ContactController {
	public static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;
	
	
	@Autowired
	private ContactRepository contactRepository;
	
	@RequestMapping(value = "/contact/", method = RequestMethod.GET)
	public Page<Contact> getEmployees(Pageable pageable){
		return contactService.getAllContact(pageable);
	}
	
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getContact(@PathVariable("id") long id){
		logger.info("Fetching Employee with id {}", id);
		Contact contact =  contactService.getContactById(id);
		if(contact == null){
			logger.error("contact with id {} not found.", id);
			return new ResponseEntity<>(new CustomErrorType("contact with id "+id+"not found"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/contact/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteContact(@PathVariable("id") long id){
		logger.info("Fetching & Deleting User with id {}", id);
		Contact contact = contactService.getContactById(id);
		if(contact == null){
			logger.error("Unable to delete. Contact with id {} not found.", id);
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Contact with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}	
		contactService.deleteContactByid(id);
		return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/contact/", method = RequestMethod.POST)
	public ResponseEntity<?> createContact(@RequestBody Contact contact, UriComponentsBuilder ucBuilder){
		logger.info("Creating Employee: {}",contact);
		contactService.saveContact(contact);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/contact/{id}").buildAndExpand(contact.getId()).toUri());
		return new ResponseEntity<String>(headers,HttpStatus.CREATED);	
	}
	
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContact(@PathVariable("id") long id, @RequestBody Contact contact) {
        logger.info("Updating Contact with id {}", id);
 
        Contact currentContact = contactService.getContactById(id);
        if (currentContact == null) {
            logger.error("Unable to update. Contact with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Contact with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        if(contact.getAddress() != null){
        	currentContact.setAddress(contact.getAddress());
        }if(contact.geteMaill() != null){
        	currentContact.seteMaill(contact.geteMaill());
        }if(contact.getFirstName() !=null){
        	currentContact.setFirstName(contact.getFirstName());
        }if(contact.getLastName() != null){
        	currentContact.setLastName(contact.getLastName());
        }if(contact.getFirstName() !=null|| contact.getLastName() !=null){
        	currentContact.setFullName();
        }if(contact.getMobileNumber() != null){
        	currentContact.setMobileNumber(contact.getMobileNumber());
        }
        contactService.updateContact(currentContact);
        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }
}