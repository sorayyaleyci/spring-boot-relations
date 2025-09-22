package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
