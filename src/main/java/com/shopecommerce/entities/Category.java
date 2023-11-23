package com.shopecommerce.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Column(name = "name",length = 45, nullable = false)
    private String mame;

    @Column(name = "description", length = 45, nullable = false)
    private String description;

    // 1 category - > N product


}
