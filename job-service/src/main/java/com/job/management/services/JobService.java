package com.job.management.services;

import com.job.management.model.Job;
import com.job.management.model.Status;
import com.job.management.payload.JobRequestPayload;
import com.job.management.payload.MessageResponse;
import com.job.management.repository.JobRepository;
import com.job.management.utility.JobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private static final String EMPLOYEE_URL = "http://localhost:8082/api/employee/manage";

    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    public MessageResponse createJob(JobRequestPayload payload) throws JobException {
        if (payload != null) {
            logger.info("Inside JobService createJob----{}",payload);
            Job job = new Job();
            job.setName(payload.getName());
            job.setJobCreatedDate(LocalDate.now());
            job.setProfitValue(payload.getProfitValue());
            job.setStatus(Status.NOT_STARTED);
            job.setRole(payload.getRole());
            job.setStarttime(LocalTime.parse(payload.getStarttime()));
            job.setEndtime(LocalTime.parse(payload.getEndtime()));

            jobRepository.save(job);
            return new MessageResponse("Job added successfully");
        } else {
            throw new JobException("Job not created.. Please try after some time!");
        }

    }

    public MessageResponse updateJob(JobRequestPayload payload , Long id) throws JobException {
        logger.info("Inside JobService updateJob----{}",payload);
        Optional<Job> job = jobRepository.findById(id);
        if (payload != null && job.isPresent()) {
            if(payload.getName()!=null && !payload.getName().isEmpty()) {
                job.get().setName(payload.getName());
            }
            if(payload.getProfitValue()!=0) {
                job.get().setProfitValue(payload.getProfitValue());
            }
            if(payload.getRole()!=null && !payload.getRole().isEmpty()) {
                job.get().setRole(payload.getRole());
            }
            if(payload.getStarttime()!=null) {
                job.get().setStarttime(LocalTime.parse(payload.getStarttime()));
            }
            if(payload.getEndtime()!=null) {
                job.get().setEndtime(LocalTime.parse(payload.getEndtime()));
            }
            job.get().setJobUpdatedDate(LocalDate.now());
            jobRepository.save(job.get());
            return new MessageResponse("Job updated successfully");
        } else {
            throw new JobException("Job not updated..Something went wrong.. Please try after some time!");
        }

    }


    public MessageResponse deleteJob(Long id) throws JobException{
        try {
            logger.info("Inside EmployeeService deleteEmployee----{}",id);
            Optional<Job> job = jobRepository.findById(id);
            if (job.isPresent()) {
                jobRepository.delete(job.get());
                return new MessageResponse("Job deleted");
            } else
                return new MessageResponse("Job not present");

        } catch (Exception e) {
            throw new JobException("Something went wrong. Please try after some time!");
        }
    }


    public Object getAllJobs() throws JobException {
        try {

            List<Job> job = jobRepository.findAll();
            if (!job.isEmpty()) {

                return job;
            } else
                return new MessageResponse("Jobs not found");

        } catch (Exception e) {
            throw new JobException("Something went wrong. Please try after some time!");
        }
    }

    public Object getJob(Long id)  throws JobException{
        try {

            Optional<Job> job = jobRepository.findById(id);
            if (job.isPresent()) {

                return job.get();
            } else
                return new MessageResponse("Employee not found");

        } catch (Exception e) {
            throw new JobException("Something went wrong. Please try after some time!");
        }
    }

    public MessageResponse processJob(Long jobId, Long userId, String status,String role) throws  JobException{
        logger.info("Inside JobService processJob----{}",jobId);
        Optional<Job> job = jobRepository.findById(jobId);

        if (job.isPresent() && role!=null && !role.isEmpty() && job.get().getRole().equals(role)) {
            if(status !=null && !status.isEmpty() && userId !=null) {
                switch (status) {

                    case "Allocate":
                        if(job.get().getStatus()==Status.NOT_STARTED){
                        List<Job> existingJob = jobRepository.findByEmployeeId(userId);
                        if (!existingJob.isEmpty()) {
                            boolean result = false;
                            for (int i = 0; i < existingJob.size(); i++) {
                                result = job.get().getStarttime().isBefore(existingJob.get(i).getEndtime());
                                if (result) {
                                    return new MessageResponse("Job can't be allocated due to time overlap");
                                }
                            }
                            job.get().setJobStartTime(LocalTime.now());
                            job.get().setStatus(Status.IN_PROGRESS);
                            job.get().setEmployeeId(userId);
                        } else {
                            job.get().setJobStartTime(LocalTime.now());
                            job.get().setStatus(Status.IN_PROGRESS);
                            job.get().setEmployeeId(userId);
                        }
                        }
                        else{
                            return new MessageResponse("Job can't be allocated as its already in progress or aborted");
                        }
                        break;

                    case "Abort":
                        if(job.get().getStatus().equals(Status.IN_PROGRESS)){
                            LocalTime time = job.get().getJobStartTime();
                            LocalTime presentTime = LocalTime.now();
                            long value= time.until(presentTime, ChronoUnit.MINUTES);
                            System.out.println("The time difference in minutes is"+ value);
                            if(value < 20) {
                                job.get().setStatus(Status.NOT_STARTED);
                                job.get().setEmployeeId(null);
                            }
                            else{
                                job.get().setStatus(Status.ABORTED);
                                job.get().setEmployeeId(null);
                            }
                        }
                        else{
                            return new MessageResponse("Job can't be aborted");
                        }

                        break;

                    case "Complete":
                        if(job.get().getStatus().equals(Status.IN_PROGRESS)) {
                            job.get().setStatus(Status.COMPLETED);
                            String url = "/updateSalary/"+userId+"/"+job.get().getProfitValue();
                            System.out.println(url);
                            String result = restTemplate.getForObject(EMPLOYEE_URL + url,String.class);
                            assert result != null;
                            job.get().setEmployeeId(null);

                        }
                        else{
                            return new MessageResponse("Job can't be completed");
                        }
                        break;


                }
            }


            jobRepository.save(job.get());

            return new MessageResponse("Job "+ status +" done");
        } else {
            throw new JobException("Job not processed...Your role is not applicable for this job!");
        }
    }

}
