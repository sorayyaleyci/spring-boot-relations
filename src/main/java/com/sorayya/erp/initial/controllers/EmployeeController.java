package com.sorayya.erp.initial.controllers;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Employee;
import com.sorayya.erp.initial.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){

        try {
            Employee createdEmp = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(createdEmp);

        }catch(ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();

        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if (employee.getEmployeeId() != null && !employee.getEmployeeId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Employee updated = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updated);

        }catch(ResourceNotFoundException exception){
            return ResponseEntity.notFound().build();

        }
    }

}
