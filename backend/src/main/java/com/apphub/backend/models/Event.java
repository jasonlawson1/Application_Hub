package com.apphub.backend.models;

import java.time.LocalDate;

import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name="Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    private String title;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private String notes;

    /*Creates a new Event instance with default field values. */
    public Event(){}

    /*Creates a new Event with all fields populated. */
    public Event(String title,  String location, LocalDate date, LocalTime startTime, String notes){
        this.title = title;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.notes = notes;
    }

    /*Returns the unique identifier of this event. */
    public Long getId(){
        return id;
    }

    /*Returns the user who owns this event. */
    public User getUser(){
        return user;
    }

    /*Returns the title of this event. */
    public String getTitle(){
        return title;
    }

    /*Returns the location of this event. */
    public String getLocation(){
        return location;
    }

    /*Returns the date of this event. */
    public LocalDate getDate(){
        return date;
    }

    /*Returns the start time of this event. */
    public LocalTime getStartTime(){
        return startTime;
    }

    /*Returns any additional notes for this event. */
    public String getNotes(){
        return notes;
    }

    /*Sets the unique identifier of this event. */
    public void setId(Long id){
        this.id = id;
    }

    /*Sets the user who owns this event. */
    public void setUser(User user){
        this.user=user;
    }

    /*Sets the title of this event. */
    public void setTitle(String title){
        this.title = title;
    }

    /*Sets the location of this event. */
    public void setLocation(String location){
        this.location = location;
    }

    /*Sets the date of this event. */
    public void setDate(LocalDate date){
        this.date = date;
    }

    /*Sets the start time of this event. */
    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    /*Sets additional notes for this event. */
    public void setNotes(String notes){
        this.notes = notes;
    }

}
