package com.shopecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopecommerce.entities.Contact;

@Repository 
public interface ContactRepo extends JpaRepository<Contact, Integer> {

}