package com.shopecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopecommerce.entities.Category;

@Repository 
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}