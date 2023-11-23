package com.shopecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopecommerce.entities.SaleOrder;

@Repository 
public interface SaleOrderRepo extends JpaRepository<SaleOrder, Integer>  {

}
