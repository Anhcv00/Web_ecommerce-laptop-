package com.shopecommerce.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "product_images")
public class ProductImages extends BaseEntity {

    @Column(name = "title" , length = 45, nullable = false)
    private String title;

    @Column(name = "path", length = 200, nullable = false)
    private String path;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "product_id")
   private Product product;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
