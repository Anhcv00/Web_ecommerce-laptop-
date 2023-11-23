package com.shopecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopecommerce.entities.User;


@Repository 
public interface UserRepo extends JpaRepository<User, Integer> {

   /* @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);*/


}