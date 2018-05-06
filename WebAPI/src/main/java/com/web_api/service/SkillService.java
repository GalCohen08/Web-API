package com.web_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.web_api.model.Skill;

public interface SkillService {
	Skill getSkillByName(Long  ContactId,Pageable pageable,String name);
	
	Page<Skill> getAllSkills(Long  ContactId,Pageable pageable);
	
	Skill saveSkill(Skill Skill);
		
	void updateSkill(Skill Skill);

	void deleteSkill(Skill skill);
}

