package com.web_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web_api.model.Contact;

@Repository("employeeRepository")
public interface ContactRepository extends JpaRepository<Contact, Long>{

}
