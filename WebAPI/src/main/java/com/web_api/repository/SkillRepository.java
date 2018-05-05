package com.web_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web_api.model.Skill;

public interface SkillRepository extends JpaRepository<Skill,Long>{
	Page<Skill> findByContactId(Long employeeId, Pageable pageable);
}
