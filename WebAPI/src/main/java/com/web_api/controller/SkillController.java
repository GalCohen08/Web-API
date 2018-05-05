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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.web_api.model.Contact;
import com.web_api.model.Skill;
import com.web_api.service.ContactServiceImpl;
import com.web_api.service.SkillServiceImpl;
import com.web_api.utils.CustomErrorType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@Api(value="CRUD for managing Skills", tags ="Operations pertaining to contacts")
public class SkillController {
	public static final Logger logger = LoggerFactory.getLogger(SkillController.class);
	
	@Autowired
	
	private SkillServiceImpl skillServiceImpl;
	
	@Autowired
	
	private ContactServiceImpl contactServiceImpl;
	
	@ApiOperation(value = "View all available Skills of a Contact", response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved Skills list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
	)
	@RequestMapping(value ="/contact/{id}/skills", method = RequestMethod.GET)
	public Page<Skill> getSkills(@PathVariable (value = "id") Long employeeId, Pageable pageable){
		return skillServiceImpl.getAllSkills(employeeId, pageable);
	}
	
	@ApiOperation(value = "Creates new skill",notes = "Skil contains name and level:{JUNIOR, INTERMEDIATE, SENIOR}")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Skill Has been Successfully created"),
		    @ApiResponse(code = 404, message = "Failed to create a new contact")
		}
	)
	@RequestMapping(value ="/contact/{id}/skills", method = RequestMethod.POST)
	public ResponseEntity<?> createSkill(@PathVariable (value = "id") Long emplyeeId,
            @Valid @RequestBody Skill skill,UriComponentsBuilder ucBuilder, Pageable pageable){
		logger.info("Creating skill: {}",skill);
		if(skillServiceImpl.getSkillByName(emplyeeId, pageable, skill.getName())!=null){
			return new ResponseEntity<>(new CustomErrorType("Unable to add. skill " + skill.getName() + " already exist"),
            HttpStatus.FOUND);
		}else{
			skill.setEmployee(contactServiceImpl.getContactById(emplyeeId));
			skillServiceImpl.saveSkill(skill);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/contact/{id}/skills").buildAndExpand(skill.getId()).toUri());
			return new ResponseEntity<String>(headers,HttpStatus.CREATED);	
		}
	}
	
	@ApiOperation(value = "View Skill content",notes ="Uses ID to find contact and name of skill to find skill", response = Contact.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully found Skill"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		    @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		    @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
		}
	)
	@RequestMapping(value = "/contact/{id}/skills/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getSkill(@PathVariable("id") long id,@PathVariable("name") String name,Pageable pageable){
		logger.info("Fetching Skill with id {}", id);
		Skill skill =  skillServiceImpl.getSkillByName(id, pageable, name);
		if(skill == null){
			logger.error("skill with name {} not found.", name);
			return new ResponseEntity<>(new CustomErrorType("Skill with name "+name+" not found"),HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete Skill")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Skill deleted Successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
		}
	)
	@RequestMapping(value = "/contact/{id}/skills/{name}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSkill(@PathVariable("id") long id,@PathVariable("name")String name, Pageable pageable){
		logger.info("Fetching & Deleting skills from Contact id: {} and skill name:{}", id,name);
		Skill skill =  skillServiceImpl.getSkillByName(id, pageable, name);
		if(skill == null){
		logger.error("Unable to delete. Skill with name {} not found.", name);
		return new ResponseEntity<>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),HttpStatus.NOT_FOUND);
		}
		skillServiceImpl.deleteSkill(skill);
		return new ResponseEntity<Skill>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "Update a Skill", notes ="Updates the Skill fields, (name OR level:{JUNIOR, INTERMEDIATE, SENIOR} )",response = Page.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Skill has been updated uccessfully"),
			@ApiResponse(code = 401, message = "You are not authorized to updated this Skill"),
		    @ApiResponse(code = 404, message = "The Skill you are trying to Update is not found")
		}
	)
    @RequestMapping(value = "/contact/{id}/skills/{name}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSkill(@PathVariable("id") long id, @RequestBody Skill skill, @PathVariable("name")String name, Pageable pageable) {
        logger.info("Updating Skill from Contact id: {} and skill name:{}", id,name);
        Skill currentSkill =  skillServiceImpl.getSkillByName(id, pageable, name);
        if (currentSkill == null) {
            logger.error("Unable to update. Skill with name: {} or contact with id: {} not found.",name, id);
            return new ResponseEntity<>(new CustomErrorType("Unable to update.contact with id " + id + "or Skill with name:"+name+" not found."),
                    HttpStatus.NOT_FOUND);
        }
        if(skill.getName() != null){
        	currentSkill.setName(skill.getName());
        }
        if(skill.getLevel() != null){
        	currentSkill.setLevel(skill.getLevel());
        }
        skillServiceImpl.updateSkill(currentSkill);
        return new ResponseEntity<Skill>(currentSkill, HttpStatus.OK);
    }
}
