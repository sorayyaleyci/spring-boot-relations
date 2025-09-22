package com.sorayya.erp.initial.repositiories;

import com.sorayya.erp.initial.entities.Employee;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("select e.city, count (e) from employee e group by e.city ")
    List<Object[]> countEmployeesByCity();

    @Query("select d.departmentName as departmentName, count (e) as count " +
            "from employee e join e.department d group by d.departmentName")
    List<Tuple> countEmployeesByDepartment();

}
