package com.apphub.backend.dto;

import java.time.LocalDate;

import java.time.LocalTime;

public class Events_request {
    private Long id;
    private Long userId;
    private String title;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private String notes;

    /*Constructs an Events_request with all event fields populated. */
    public Events_request(String title, String location, LocalDate date, LocalTime startTime, String notes, Long userId, Long id) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.notes = notes;
        this.userId = userId;
        this.id = id;
    }

    /*Returns the unique identifier of the event. */
    public Long getId(){
        return id;
    }

    /*Returns the ID of the user associated with this event. */
    public Long getUserId(){
        return userId;
    }

    /*Returns the location of the event. */
    public String getLocation(){
        return location;
    }

    /*Returns the date of the event. */
    public LocalDate getDate(){
        return date;
    }

    /*Returns the title of the event. */
    public String getTitle(){
        return title;
    }

    /*Returns the start time of the event. */
    public LocalTime getStartTime(){
        return startTime;
    }

    /*Returns any notes associated with the event. */
    public String getNotes(){
        return notes;
    }

    /*Sets the location of the event. */
    public void setLocation(String location){
        this.location = location;
    }

    /*Sets the date of the event. */
    public void setDate(LocalDate date){
        this.date = date;
    }

    /*Sets the unique identifier of the event. */
    public void setId(Long id){
        this.id = id;
    }

    /*Sets the ID of the user associated with this event. */
    public void setUserId(Long userId){
        this.userId=userId;
    }

    /*Sets the title of the event. */
    public void setTitle(String title){
        this.title = title;
    }

    /*Sets the start time of the event. */
    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    /*Sets the notes associated with the event. */
    public void setNotes(String notes){
        this.notes = notes;
    }


}
