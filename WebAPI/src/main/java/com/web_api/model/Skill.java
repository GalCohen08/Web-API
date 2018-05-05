package com.web_api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Enumerated;

@Entity
@Table(name = "skill")
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	private long id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Level level;
	
	@ManyToOne
	@JoinColumn (name="contact_id")
	@JsonBackReference
	private Contact contact;
	
	public Skill() {
		super();
	}
	
	public Skill(String name, Level level, Contact contact) {
		super();
		this.name = name;
		this.level = level;
		this.contact = contact;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Contact getEmployee() {
		return contact;
	}

	public void setEmployee(Contact employee) {
		this.contact = employee;
	}	
}
