package com.user.management.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

public class JobRequestPayload {
    @NotBlank
    @Size(max = 20)
    private String name;

//    @JsonFormat(pattern = "HH:mm:ss")
    @NotBlank
    private String starttime;

//    @JsonFormat(pattern = "HH:mm:ss")
    @NotBlank
    private String endtime;

    @NotBlank
    private double profitValue;

    @Size(max = 20)
    @NotBlank
    private String role;

    public JobRequestPayload() {
    }

    public JobRequestPayload(String name, String starttime, String endtime, double profitValue, String role) {
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

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
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
