package com.job.management.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.job.management.payload.JobRequestPayload;
import com.job.management.payload.MessageResponse;
import com.job.management.services.JobService;
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

@ContextConfiguration(classes = {JobController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class JobControllerTest {
    @Autowired
    private JobController jobController;

    @MockBean
    private JobService jobService;


    @Test
    public void testCreateJob() throws Exception {
        when(jobService.createJob((JobRequestPayload) any()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));

        JobRequestPayload jobRequestPayload = new JobRequestPayload();
        jobRequestPayload.setEndtime("Endtime");
        jobRequestPayload.setName("Name");
        jobRequestPayload.setProfitValue(10.0d);
        jobRequestPayload.setRole("Role");
        jobRequestPayload.setStarttime("Starttime");
        String content = (new ObjectMapper()).writeValueAsString(jobRequestPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/job/manage/createJob")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(jobController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Job added successfully!"));
    }


    @Test
    public void testUpdateJob() throws Exception {
        when(jobService.updateJob((JobRequestPayload) any(), (Long) any()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));

        JobRequestPayload jobRequestPayload = new JobRequestPayload();
        jobRequestPayload.setEndtime("Endtime");
        jobRequestPayload.setName("Name");
        jobRequestPayload.setProfitValue(10.0d);
        jobRequestPayload.setRole("Role");
        jobRequestPayload.setStarttime("Starttime");
        String content = (new ObjectMapper()).writeValueAsString(jobRequestPayload);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/job/manage/updateJob/{id}", "42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Job updated successfully!"));
    }


    @Test
    public void testDeleteJob() throws Exception {
        when(jobService.deleteJob((Long) any())).thenReturn(new MessageResponse("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/job/manage/deleteJob/{id}",
                "42");
        MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Job deleted successfully!"));
    }


    @Test
    public void testGetAllJobs() throws Exception {
        when(jobService.getAllJobs()).thenReturn("All Jobs");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/job/manage/getAllJobs");
        MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("All Jobs"));
    }


    @Test
    public void testGetAllJobs2() throws Exception {
        when(jobService.getAllJobs()).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/job/manage/getAllJobs");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Failed to retrieve list of jobs!"));
    }

    /**
     * Method under test: {@link JobController#getJob(String)}
     */
    @Test
    public void testGetJob() throws Exception {
        when(jobService.getJob((Long) any())).thenReturn("Job");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/job/manage/getJob/{id}", "42");
        MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Job"));
    }

    /**
     * Method under test: {@link JobController#getJob(String)}
     */
    @Test
    public void testGetJob2() throws Exception {
        when(jobService.getJob((Long) any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/job/manage/getJob/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Failed to retrieve job!"));
    }

    /**
     * Method under test: {@link JobController#processJob(String, String, String, String)}
     */
    @Test
    public void testProcessJob() throws Exception {
        when(jobService.processJob((Long) any(), (Long) any(), (String) any(), (String) any()))
                .thenReturn(new MessageResponse("Not all who wander are lost"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/job/manage/processJob/{jobId}/{userId}/{status}/{role}", "42", "42", "Status", "Role");
        MockMvcBuilders.standaloneSetup(jobController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Not all who wander are lost"));
    }
}

