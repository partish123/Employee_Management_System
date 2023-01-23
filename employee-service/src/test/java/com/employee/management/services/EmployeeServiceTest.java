package com.employee.management.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employee.management.models.Employee;
import com.employee.management.payload.request.CreateEmployeePayload;
import com.employee.management.payload.request.UpdateEmployeePayload;
import com.employee.management.payload.response.MessageResponse;
import com.employee.management.repository.EmployeeRepository;
import com.employee.management.utility.EmployeeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Rule;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {EmployeeService.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Method under test: {@link EmployeeService#createEmployee(CreateEmployeePayload)}
     */
    @Test
    public void testCreateEmployee() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee);

        CreateEmployeePayload createEmployeePayload = new CreateEmployeePayload();
        createEmployeePayload.setEmail("jane.doe@example.org");
        createEmployeePayload.setFirstname("Jane");
        createEmployeePayload.setLastname("Doe");
        createEmployeePayload.setPassword("iloveyou");
        createEmployeePayload.setUsername("janedoe");
        assertEquals("Employee added successfully", employeeService.createEmployee(createEmployeePayload).getMessage());
        verify(employeeRepository).save((Employee) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testUpdateEmployee2() throws EmployeeException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.isPresent()" because "employee" is null
        //       at com.employee.management.services.EmployeeService.updateEmployee(EmployeeService.java:51)
        //   See https://diff.blue/R013 to resolve this issue.

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee);
        when(employeeRepository.findById((Long) any())).thenReturn(null);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        employeeService.updateEmployee(updateEmployeePayload, 123L);
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee3() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee);
        when(employeeRepository.findById((Long) any())).thenReturn(Optional.empty());

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        thrown.expect(EmployeeException.class);
        employeeService.updateEmployee(updateEmployeePayload, 123L);
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee4() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail(null);
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee5() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee6() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname(null);
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee7() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee8() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname(null);
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateEmployee(UpdateEmployeePayload, Long)}
     */
    @Test
    public void testUpdateEmployee9() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        assertEquals("Employee updated successfully",
                employeeService.updateEmployee(updateEmployeePayload, 123L).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#deleteEmployee(Long)}
     */
    @Test
    public void testDeleteEmployee() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);
        doNothing().when(employeeRepository).delete((Employee) any());
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals("Employee deleted", employeeService.deleteEmployee(123L).getMessage());
        verify(employeeRepository).findById((Long) any());
        verify(employeeRepository).delete((Employee) any());
    }

    /**
     * Method under test: {@link EmployeeService#deleteEmployee(Long)}
     */
    @Test
    public void testDeleteEmployee2() throws EmployeeException {
        doNothing().when(employeeRepository).delete((Employee) any());
        when(employeeRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertEquals("Employee not present", employeeService.deleteEmployee(123L).getMessage());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#getAllEmployees()}
     */
    @Test
    public void testGetAllEmployees() throws EmployeeException {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals("Employees not found", ((MessageResponse) employeeService.getAllEmployees()).getMessage());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeService#getAllEmployees()}
     */
    @Test
    public void testGetAllEmployees2() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);

        ArrayList<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        Object actualAllEmployees = employeeService.getAllEmployees();
        assertSame(employeeList, actualAllEmployees);
        assertEquals(1, ((Collection<Employee>) actualAllEmployees).size());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeService#getEmployee(Long)}
     */
    @Test
    public void testGetEmployee() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(employee, employeeService.getEmployee(123L));
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#getEmployee(Long)}
     */
    @Test
    public void testGetEmployee2() throws EmployeeException {
        when(employeeRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertEquals("Employee not found", ((MessageResponse) employeeService.getEmployee(123L)).getMessage());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateSalary(long, double)}
     */
    @Test
    public void testUpdateSalary() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals("Employee salary updated successfully", employeeService.updateSalary(123L, 10.0d).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateSalary(long, double)}
     */
    @Test
    public void testUpdateSalary2() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(0.0d);
        Optional<Employee> ofResult = Optional.of(employee);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstname("Jane");
        employee1.setId(123L);
        employee1.setLastname("Doe");
        employee1.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals("Employee salary updated successfully", employeeService.updateSalary(123L, 10.0d).getMessage());
        verify(employeeRepository).save((Employee) any());
        verify(employeeRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link EmployeeService#updateSalary(long, double)}
     */
    @Test
    public void testUpdateSalary3() throws EmployeeException {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstname("Jane");
        employee.setId(123L);
        employee.setLastname("Doe");
        employee.setSalary(10.0d);
        when(employeeRepository.save((Employee) any())).thenReturn(employee);
        when(employeeRepository.findById((Long) any())).thenReturn(Optional.empty());
        thrown.expect(EmployeeException.class);
        employeeService.updateSalary(123L, 10.0d);
        verify(employeeRepository).findById((Long) any());
    }
}

