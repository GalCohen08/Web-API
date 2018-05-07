package com.web_api.controller;

import javax.validation.Valid;

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
import com.web_api.repository.SkillRepository;
import com.web_api.service.ContactService;
import com.web_api.utils.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/api") 
@Api(value="CRUD for managing contacts", tags ="Operations pertaining to contacts")
public class ContactController {
	public static final Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@ApiOperation(value = "View all available Contacts", response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved Contacts list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
	)
	@RequestMapping(value = "/contact/", method = RequestMethod.GET)
	public Page<Contact> getContactsList(Pageable pageable){
		return contactService.getAllContact(pageable);
	}
	
	@ApiOperation(value = "Uses ID to find contact, View contact content", response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully found Contact"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
	)
	@RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getContact(@ApiParam(value = "Contact Id")@PathVariable("id") long id){
		logger.info("Fetching Employee with id {}", id);
		Contact contact =  contactService.getContactById(id);
		if(contact == null){
			logger.error("contact with id {} not found.", id);
			return new ResponseEntity<>(new CustomErrorType("contact with id "+id+"not found"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete the contact")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contact deleted Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
		}
	)
	@RequestMapping(value = "/contact/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteContact(@ApiParam(value = "Contact Id")@PathVariable("id") long id){
		logger.info("Fetching & Deleting User with id {}", id);
		Contact contact = contactService.getContactById(id);
		if(contact == null){
			logger.error("Unable to delete. Contact with id {} not found.", id);
			return new ResponseEntity<>(new CustomErrorType("Unable to delete. Contact with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}	
		skillRepository.deleteAll(contact.getSkills());
		contactService.deleteContactByid(id);
		return new ResponseEntity<Contact>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Creates new contact",notes = "Contact contains firstName, lastName, address, email, mobileNumber")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contact Has been Successfully created"),
		    @ApiResponse(code = 404, message = "Failed to create a new contact")
		}
	)
	@RequestMapping(value = "/contact/", method = RequestMethod.POST)
	public ResponseEntity<?> createContact(@Valid @ApiParam(value = "Creates new contact (firstName, lastName, address, eMaill, mobileNumber)")@RequestBody Contact contact, UriComponentsBuilder ucBuilder){
		logger.info("Creating Employee: {}",contact);
		if(contactService.getContactByEmail( contact.getEmail())!=null){
			return new ResponseEntity<>(new CustomErrorType("Unable to add contact. contact with Email " + contact.getEmail() + "is already exist"),HttpStatus.FOUND);
		}
		if(contactService.getContactByPhone(contact.getMobileNumber())!=null){
			return new ResponseEntity<>(new CustomErrorType("Unable to add contact. contact with Mobile Number " + contact.getMobileNumber() + "is already exist"),HttpStatus.FOUND);
		}
		contactService.saveContact(contact);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/contact/{id}").buildAndExpand(contact.getId()).toUri());
		return new ResponseEntity<String>(headers,HttpStatus.CREATED);	
	}
	
	@ApiOperation(value = "Update a Contact", notes ="Updates the contact fields, (one field or more)",response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contact has been updated uccessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to updated this Contact"),
		    @ApiResponse(code = 404, message = "The Contact you are trying to Update is not found")
		}
	)
    @RequestMapping(value = "/contact/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContact(@ApiParam(value = "Contact Id")@PathVariable("id") long id, @ApiParam(value = "Updates one field or more", required = true) @RequestBody Contact contact) {
        logger.info("Updating Contact with id {}", id);
        Contact currentContact = contactService.getContactById(id);
        if (currentContact == null) {
            logger.error("Unable to update. Contact with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Contact with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        if(contact.getAddress() != null){
        	currentContact.setAddress(contact.getAddress());
        }
        if(contact.getEmail() != null){
        	currentContact.setEmail(contact.getEmail());
        }
        if(contact.getFirstName() !=null){
        	currentContact.setFirstName(contact.getFirstName());
        }
        if(contact.getLastName() != null){
        	currentContact.setLastName(contact.getLastName());
        }
        if(contact.getFirstName() !=null|| contact.getLastName() !=null){
        	currentContact.setFullName();
        }
        if(contact.getMobileNumber() != null){
        	currentContact.setMobileNumber(contact.getMobileNumber());
        }
        contactService.updateContact(currentContact);
        return new ResponseEntity<Contact>(currentContact, HttpStatus.OK);
    }
}
