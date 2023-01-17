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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class JobService {

    @Autowired
    JobRepository jobRepository;

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
            job.setStarttime(payload.getStarttime());
            job.setEndtime(payload.getEndtime());

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
                job.get().setStarttime(payload.getStarttime());
            }
            if(payload.getEndtime()!=null) {
                job.get().setEndtime(payload.getEndtime());
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

}
