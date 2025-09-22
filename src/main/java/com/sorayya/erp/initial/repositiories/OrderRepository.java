package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
