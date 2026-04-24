package com.apphub.backend.dto;
//This class represents application data the user sends to the server
import java.time.LocalDate;


public class Application_Request {
   private String jobTitle;
    private String company;
    private String status;
    private LocalDate dateApplied;
    private LocalDate deadline;
    private String location;
    private String notes;
    private Long userId;

    /* Returns the job title of the application. */
    public String getJobTitle() { return jobTitle; }
    /* Returns the company name of the application. */
    public String getCompany() { return company; }
    /* Returns the current status of the application. */
    public String getStatus() { return status; }
    /* Returns the date the application was submitted. */
    public LocalDate getDateApplied() { return dateApplied; }
    /* Returns the deadline date for the application. */
    public LocalDate getDeadline() { return deadline; }
    /* Returns the job location. */
    public String getLocation() { return location; }
    /* Returns any notes attached to the application. */
    public String getNotes() { return notes; }
    /* Returns the ID of the user who owns this application. */
    public Long getUserId() { return userId; }

    /* Sets the job title of the application. */
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    /*Sets the company name of the application. */
    public void setCompany(String company) { this.company = company; }
    /* Sets the current status of the application. */
    public void setStatus(String status) { this.status = status; }
    /*Sets the date the application was submitted. */
    public void setDateApplied(LocalDate dateApplied) { this.dateApplied = dateApplied; }
    /*Sets the deadline date for the application. */
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    /*Sets the job location. */
    public void setLocation(String location) { this.location = location; }
    /*Sets the notes attached to the application. */
    public void setNotes(String notes) { this.notes = notes; }
    /*Sets the ID of the user who owns this application. */
    public void setUserId(Long userId) { this.userId = userId; }
}
