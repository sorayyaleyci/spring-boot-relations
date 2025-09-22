package com.sorayya.erp.initial.services;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.entities.Employee;
import com.sorayya.erp.initial.entities.EmployeeDetail;
import com.sorayya.erp.initial.repositiories.DepartmentRepository;
import com.sorayya.erp.initial.repositiories.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Employee saveEmployee(Employee employee) throws ResourceNotFoundException {
        // 1. Resolve department from DB
        if (employee.getDepartment() != null && employee.getDepartment().getDepartmentId() != null) {
            Long deptId = employee.getDepartment().getDepartmentId();
            Department managedDept = departmentRepository.findById(deptId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + deptId));
            employee.setDepartment(managedDept); // assign managed entity
        }

        // 2. EmployeeDetail will be persisted automatically because cascade = ALL
        return employeeRepository.save(employee);
    }


    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee data) throws ResourceNotFoundException {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        existing.setName(data.getName());
        // 3. Update department (fetch managed entity from DB)
        if (data.getDepartment() != null && data.getDepartment().getDepartmentId() != null) {
            Long deptId = data.getDepartment().getDepartmentId();
            Department dept = departmentRepository.findById(deptId)
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + deptId));
            existing.setDepartment(dept);
        } else {
            existing.setDepartment(null); // optional: clear department if none given
        }

        // 4. Update employee detail (cascade = ALL, so just set it back)
        if (data.getEmployeeDetail() != null) {
            EmployeeDetail newDetail = data.getEmployeeDetail();
            EmployeeDetail existingDetail = existing.getEmployeeDetail();

            if (existingDetail == null) {
                existingDetail = new EmployeeDetail();
            }
            existingDetail.setAddress(newDetail.getAddress());
            existingDetail.setEmail(newDetail.getEmail());
            existingDetail.setNationalId(newDetail.getNationalId());

            existing.setEmployeeDetail(existingDetail);
        } else {
            existing.setEmployeeDetail(null); // optional: remove details if payload has none
        }

        return employeeRepository.save(existing);
    }
}
