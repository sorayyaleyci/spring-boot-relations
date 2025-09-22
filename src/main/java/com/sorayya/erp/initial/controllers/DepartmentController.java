package com.sorayya.erp.initial.controllers;

import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.services.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping
    public List<Department> getAllEmployees(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getEmployeeById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }
}
