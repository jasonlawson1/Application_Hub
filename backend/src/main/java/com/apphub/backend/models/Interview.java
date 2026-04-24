package com.apphub.backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name="Interviews")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String jobTitle;
    private LocalDate interviewDate;
    private LocalTime interviewTime;
    private String interviewType;
    private String location;
    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "application_id")
    @JsonBackReference
    private Application application;

    /*Creates a new Interview instance with default field values. */
    public Interview() {
    }

    /*Creates a new Interview with all fields populated, linked to the given application. */
    public Interview(String company, String jobTitle, LocalDate interviewDate,
                     LocalTime interviewTime, String interviewType,
                     String location, String status, String notes,
                     Application application) {
        this.company = company;
        this.jobTitle = jobTitle;
        this.interviewDate = interviewDate;
        this.interviewTime = interviewTime;
        this.interviewType = interviewType;
        this.location = location;
        this.status = status;
        this.notes = notes;
        this.application = application;
    }

    /*Returns the unique identifier of this interview. */
    public Long getId() {
        return id;
    }

    /*Returns the company name for this interview. */
    public String getCompany() {
        return company;
    }

    /*Sets the company name for this interview. */
    public void setCompany(String company) {
        this.company = company;
    }

    /*Returns the job title for this interview. */
    public String getJobTitle() {
        return jobTitle;
    }

    /*Sets the job title for this interview. */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /*Returns the scheduled date of this interview. */
    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    /*Sets the scheduled date of this interview. */
    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    /*Returns the scheduled time of this interview. */
    public LocalTime getInterviewTime() {
        return interviewTime;
    }

    /*Sets the scheduled time of this interview. */
    public void setInterviewTime(LocalTime interviewTime) {
        this.interviewTime = interviewTime;
    }

    /*Returns the format or type of this interview (e.g., phone, on-site). */
    public String getInterviewType() {
        return interviewType;
    }

    /*Sets the format or type of this interview. */
    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    /*Returns the location of this interview. */
    public String getLocation() {
        return location;
    }

    /*Sets the location of this interview. */
    public void setLocation(String location) {
        this.location = location;
    }

    /*Returns the current status of this interview. */
    public String getStatus() {
        return status;
    }

    /*Sets the current status of this interview. */
    public void setStatus(String status) {
        this.status = status;
    }

    /*Returns any additional notes for this interview. */
    public String getNotes() {
        return notes;
    }

    /*Sets additional notes for this interview. */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*Returns the application this interview is associated with. */
    public Application getApplication() {
        return application;
    }

    /*Sets the application this interview is associated with. */
    public void setApplication(Application application) {
        this.application = application;
    }
}
