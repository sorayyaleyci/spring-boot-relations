package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {
}
