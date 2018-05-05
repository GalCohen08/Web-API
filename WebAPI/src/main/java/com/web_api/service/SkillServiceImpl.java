package com.web_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.web_api.model.Skill;
import com.web_api.repository.SkillRepository;

@Service("skillService")
public class SkillServiceImpl  implements SkillService{
	@Autowired
	SkillRepository skillRepository;
	
	@Override
	public Skill getSkillByName(Long  employeeId,Pageable pageable,String name) {
		List<Skill> skillList = skillRepository.findByContactId(employeeId, pageable).getContent();
		for(Skill skill : skillList){
		if(name.compareTo(skill.getName()) ==0){
			return skill;
		}
	}
	return null;
	}

	@Override
	public void deleteSkill(Skill skill){ 	
		skillRepository.delete(skill);
	}

	@Override
	public Page<Skill> getAllSkills(Long  employeeId,Pageable pageable) {
		return skillRepository.findByContactId(employeeId, pageable);	
	}

	@Override
	public Skill saveSkill(Skill skill) {
		return skillRepository.save(skill);
		
	}

	@Override
	public void updateSkill(Skill skill) {
		skillRepository.save(skill);	
	}
}
