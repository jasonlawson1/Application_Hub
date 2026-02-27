package com.apphub.backend.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first;
    private String last;
    private String email;
    private String password;
    private String confirm_password;

    public Users(){
    
    }

    public Long getID(){return id;}
    
    public void setFirst(String first){
        this.first=first;
    }
    public String getFirst(){return first;}

    public void setLast(String last){
        this.last=last;
    }
    public String getLast(){return last;}


    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){return email;}

    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){return password;}

    public void setConfirmPassword(String confirm_password){
        this.confirm_password=confirm_password;
    }
    public String getConfirmPassword(){return confirm_password;}

}
