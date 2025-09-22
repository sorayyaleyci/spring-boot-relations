package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
