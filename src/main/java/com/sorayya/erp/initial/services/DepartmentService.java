package com.sorayya.erp.initial.services;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.repositiories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
    public void deleteDepartment(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        if(!dept.getEmployee().isEmpty())
            throw new IllegalStateException("The department has employees");
        departmentRepository.delete(dept);
    }

}
