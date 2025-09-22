package com.sorayya.erp.initial;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sorayya.erp.initial.common.ResourceNotFoundException;
import com.sorayya.erp.initial.entities.Department;
import com.sorayya.erp.initial.entities.Employee;
import com.sorayya.erp.initial.entities.EmployeeDetail;
import com.sorayya.erp.initial.repositiories.DepartmentRepository;
import com.sorayya.erp.initial.repositiories.EmployeeRepository;
import com.sorayya.erp.initial.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testUpdateEmployee_Success() {
        // Mock existing employee
        Employee existing = new Employee();
        existing.setEmployeeId(1L);
        existing.setName("Old Name");

        EmployeeDetail detail = new EmployeeDetail();
        detail.setAddress("Old Address");
        existing.setEmployeeDetail(detail);

        Department dept = new Department();
        dept.setDepartmentId(2L);
        dept.setDepartmentName("Sales");

        Employee updatedData = new Employee();
        updatedData.setName("Alice Updated");
        updatedData.setDepartment(new Department());
        updatedData.getDepartment().setDepartmentId(2L);

        EmployeeDetail newDetail = new EmployeeDetail();
        newDetail.setAddress("New Address");
        updatedData.setEmployeeDetail(newDetail);

        // Mock repository behavior
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(2L)).thenReturn(Optional.of(dept));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));
        try {
            Employee result = employeeService.updateEmployee(1L, updatedData);
            assertEquals("Alice Updated", result.getName());
            assertEquals("Sales", result.getDepartment().getDepartmentName());
            assertEquals("New Address", result.getEmployeeDetail().getAddress());


        }catch (ResourceNotFoundException exception){
            fail();
        }

        verify(employeeRepository).save(existing);
    }

    @Test
    public void testUpdateEmployee_EmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        Employee updatedData = new Employee();

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updatedData);
        });

        verify(employeeRepository, never()).save(any());
    }

    @Test
    public void testUpdateEmployee_DepartmentNotFound() {
        Employee existing = new Employee();
        existing.setEmployeeId(1L);

        Employee updatedData = new Employee();
        updatedData.setDepartment(new Department());
        updatedData.getDepartment().setDepartmentId(99L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(1L, updatedData);
        });

        verify(employeeRepository, never()).save(any());
    }
}

