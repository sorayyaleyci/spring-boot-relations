package com.sorayya.erp.initial.services;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.repositiories.DepartmentRepository;
import com.sorayya.erp.initial.repositiories.EmployeeRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }


    @Transactional
    public void deleteDepartment(Long id) throws ResourceNotFoundException {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        if(!dept.getEmployee().isEmpty())
            throw new IllegalStateException("The department has employees");
        departmentRepository.delete(dept);
    }

}
