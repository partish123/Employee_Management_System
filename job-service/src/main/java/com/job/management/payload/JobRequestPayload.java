package com.job.management.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public class JobRequestPayload {
    @NotBlank
    @Size(max = 20)
    private String name;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotBlank
    private LocalTime starttime;

    @JsonFormat(pattern = "HH:mm:ss")
    @NotBlank
    private LocalTime endtime;

    @NotBlank
    private double profitValue;

    @Size(max = 20)
    @NotBlank
    private String role;

    public JobRequestPayload() {
    }

    public JobRequestPayload(String name, LocalTime starttime, LocalTime endtime, double profitValue, String role) {
        this.name = name;
        this.starttime = starttime;
        this.endtime = endtime;
        this.profitValue = profitValue;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalTime starttime) {
        this.starttime = starttime;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }

    public double getProfitValue() {
        return profitValue;
    }

    public void setProfitValue(double profitValue) {
        this.profitValue = profitValue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
