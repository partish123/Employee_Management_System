package com.employee.management.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.employee.management.payload.request.CreateEmployeePayload;
import com.employee.management.payload.request.UpdateEmployeePayload;
import com.employee.management.payload.response.MessageResponse;
import com.employee.management.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;


    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee((CreateEmployeePayload) any()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));

        CreateEmployeePayload createEmployeePayload = new CreateEmployeePayload();
        createEmployeePayload.setEmail("jane.doe@example.org");
        createEmployeePayload.setFirstname("Jane");
        createEmployeePayload.setLastname("Doe");
        createEmployeePayload.setPassword("iloveyou");
        createEmployeePayload.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(createEmployeePayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employee/manage/createEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee added successfully!"));
    }


    @Test
    public void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee((UpdateEmployeePayload) any(), (Long) any()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));

        UpdateEmployeePayload updateEmployeePayload = new UpdateEmployeePayload();
        updateEmployeePayload.setEmail("jane.doe@example.org");
        updateEmployeePayload.setFirstname("Jane");
        updateEmployeePayload.setLastname("Doe");
        updateEmployeePayload.setPassword("iloveyou");
        updateEmployeePayload.setRole(new HashSet<>());
        updateEmployeePayload.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(updateEmployeePayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/employee/manage/updateEmployee/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee added successfully!"));
    }


    @Test
    public void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee((Long) any())).thenReturn(new MessageResponse("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/employee/manage/deleteEmployee/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee added successfully!"));
    }


    @Test
    public void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn("All Employees");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/manage/getAllEmployees");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("All Employees"));
    }


    @Test
    public void testGetAllEmployees2() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/manage/getAllEmployees");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Failed to retrieve list of employees!"));
    }


    @Test
    public void testGetEmployee() throws Exception {
        when(employeeService.getEmployee((Long) any())).thenReturn("Employee");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/manage/getEmployee/{id}",
                "42");
        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee"));
    }


    @Test
    public void testGetEmployee2() throws Exception {
        when(employeeService.getEmployee((Long) any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/employee/manage/getEmployee/{id}",
                "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Failed to retrieve employee!"));
    }


    @Test
    public void testUpdateSalary2() throws Exception {
        when(employeeService.updateSalary(anyLong(), anyDouble()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/employee/manage/updateSalary/{empId}/{salary}", 42, 42, "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee salary updated successfully!"));
    }


    @Test
    public void testUpdateSalary() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/employee/manage/updateSalary/{empId}/{salary}", "", "Uri Vars", "Uri Vars");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

