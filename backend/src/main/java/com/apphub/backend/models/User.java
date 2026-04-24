package com.apphub.backend.models;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="Users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first;
    private String last;

    @Column(nullable = false)
    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Application> applications;

    /*Creates a new User with the given name, email, and password. */
    public User(String first, String last, String email, String password){
        this.first= first;
        this.last=last;
        this.email=email;
        this.password=password;

    }

    /*Creates a new User instance with default field values. */
    public User(){}

    /*Returns the unique identifier of this user. */
    public Long getID(){return id;}

    /*Sets the first name of this user. */
    public void setFirst(String first){
        this.first=first;
    }

    /*Returns the first name of this user. */
    public String getFirst(){return first;}

    /*Sets the last name of this user. */
    public void setLast(String last){
        this.last=last;
    }

    /*Returns the last name of this user. */
    public String getLast(){return last;}

    /*Sets the email address of this user. */
    public void setEmail(String email){
        this.email=email;
    }

    /*Returns the email address of this user. */
    public String getEmail(){return email;}

    /*Sets the password of this user. */
    public void setPassword(String password){
        this.password=password;
    }

    /*Returns the password of this user. */
    public String getPassword(){return password;}



}
