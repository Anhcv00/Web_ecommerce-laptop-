package com.shopecommerce.controller.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.shopecommerce.entities.Category;
import com.shopecommerce.entities.Contact;
import com.shopecommerce.entities.Product;
import com.shopecommerce.entities.Role;
import com.shopecommerce.entities.User;
import com.shopecommerce.repositories.CategoryRepo;
import com.shopecommerce.repositories.ContactRepo;
import com.shopecommerce.repositories.ProductRepo;
import com.shopecommerce.repositories.RoleRepo;
import com.shopecommerce.repositories.UserRepo;
import com.shopecommerce.services.ProductService;


public abstract class BaseController {
	
	@Autowired //lấy bean từ container's spring.
	CategoryRepo categoryRepo;
		
	@Autowired
	ContactRepo contactRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	ProductService productService; 
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;
	
	
	@ModelAttribute("categories")
	public List<Category> getCategories() {
		return categoryRepo.findAll();
	}
	
	@ModelAttribute("contacts")
	public List<Contact> getContacts() {
		return contactRepo.findAll();
	}
	
	@ModelAttribute("products")
	public List<Product> getProducts() {
		return productService.findProductByStatus();
	}						
	
	@ModelAttribute("users")
	public List<User> getUsers() {
		return userRepo.findAll();
	}
	
	@ModelAttribute("roles")
	public List<Role> getRoles() {
		return roleRepo.findAll();
	}

	
	
	
}
