package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
