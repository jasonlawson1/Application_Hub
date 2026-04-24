package com.apphub.backend.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name="Applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String company;
    private String status;
    private LocalDate dateApplied;
    private LocalDate deadline;
    private String location;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Interview> interviews = new ArrayList<>();

    /*Creates a new Application instance with default field values. */
    public Application() {
    }

    /*Creates a new Application with all core fields populated. */
    public Application(String jobTitle, String company, String status,
                       LocalDate dateApplied, LocalDate deadline,
                       String location, String notes) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.status = status;
        this.dateApplied = dateApplied;
        this.deadline = deadline;
        this.location = location;
        this.notes = notes;
    }

    /*Returns the unique identifier of this application. */
    public Long getId(){
        return id;
    }

    /*Sets the unique identifier of this application. */
    public void setId(Long id){
        this.id=id;
    }

    /*Returns the user who owns this application. */
    public User getUser() {
    return user;
    }

    /*Sets the user who owns this application. */
    public void setUser(User user) {
        this.user = user;
    }

    /*Returns the job title for this application. */
    public String getJobTitle() {
        return jobTitle;
    }

    /*Sets the job title for this application. */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /*Returns the company name for this application. */
    public String getCompany() {
        return company;
    }

    /*Sets the company name for this application. */
    public void setCompany(String company) {
        this.company = company;
    }

    /*Returns the current status of this application. */
    public String getStatus() {
        return status;
    }

    /*Sets the current status of this application. */
    public void setStatus(String status) {
        this.status = status;
    }

    /*Returns the date this application was submitted. */
    public LocalDate getDateApplied() {
        return dateApplied;
    }

    /*Sets the date this application was submitted. */
    public void setDateApplied(LocalDate dateApplied) {
        this.dateApplied = dateApplied;
    }

    /*Returns the application deadline date. */
    public LocalDate getDeadline() {
        return deadline;
    }

    /*Sets the application deadline date. */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /*Returns the job location for this application. */
    public String getLocation() {
        return location;
    }

    /*Sets the job location for this application. */
    public void setLocation(String location) {
        this.location = location;
    }

    /*Returns any additional notes for this application. */
    public String getNotes() {
        return notes;
    }

    /*Sets additional notes for this application. */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*Returns the list of interviews associated with this application. */
    public List<Interview> getInterviews() {
        return interviews;
    }

    /*Sets the list of interviews associated with this application. */
    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }
}
