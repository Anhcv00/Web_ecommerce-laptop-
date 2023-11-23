package com.shopecommerce.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopecommerce.common.Utilities;
import com.shopecommerce.entities.Category;
import com.shopecommerce.repositories.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;
	@PersistenceContext
	protected EntityManager entityManager;

	public Category findCategoryBySeo(final String seo) {

//		String jpql = "Select p from Product p where p.seo = '" + seo + "'";
//		Query query = entityManager.createQuery(jpql, Product.class);

		String sql = "select * from tbl_category where seo = '" + seo + "'";
		Query query = entityManager.createNativeQuery(sql, Category.class);
		return (Category) query.getSingleResult();
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveCategory(Category category) throws Exception {
		try {
			category.setSeo(Utilities.createSeoLinkCategory(category.getName()));
			
			categoryRepo.save(category);
		} catch (Exception e) {
			throw e;
		}
	}
}
