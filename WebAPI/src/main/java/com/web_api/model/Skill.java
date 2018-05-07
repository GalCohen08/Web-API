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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Enumerated;

@Entity
@Table(name = "skill")
public class Skill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "skill_id")
	@ApiModelProperty(notes = "The database generated skill ID")
	private long id;
	@ApiModelProperty(notes = "Skill name")
	@NotEmpty(message = "name must not be empty")
	private String name;
	@ApiModelProperty(notes = "Skill Level:{JUNIOR, INTERMEDIATE, SENIOR}")
	@Enumerated(EnumType.STRING)
	private Level level;
	
	@ManyToOne
	@JoinColumn (name="contact_id")
	@JsonBackReference
	@ApiModelProperty(notes = "The Contact that is related to this skill")
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}	
}
