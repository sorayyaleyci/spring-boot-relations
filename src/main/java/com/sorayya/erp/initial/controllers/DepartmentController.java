package com.sorayya.erp.initial.controllers;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.noContent().build(); // 204 on success
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (IllegalStateException ex) { // e.g. has employees
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build(); // 204 on success
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }
}
