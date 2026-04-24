package com.apphub.backend.models;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name="Password_reset_tokens")
public class Forgot_password_token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String email;
    private String code;
    private LocalDateTime expirationTime;

    /*Creates a new Forgot_password_token instance with default field values. */
    public Forgot_password_token(){}

    /*Creates a new Forgot_password_token with the given reset code, expiration, user, and email. */
    public Forgot_password_token( String code, LocalDateTime expirationTime, User user, String email){
        this.code = code;
        this.expirationTime = expirationTime;
        this.user = user;
        this.email = email;

    }

    /*Returns the email address associated with this password reset token. */
    public String getEmail(){
        return email;
    }

    /*Returns the reset code for this password reset token. */
    public String getCode(){
        return code;
    }

    /*Returns the expiration timestamp of this password reset token. */
    public LocalDateTime getExpirationTime(){
        return expirationTime;

    }

    /*Sets the email address associated with this password reset token. */
    public void setEmail(String email){
        this.email=email;
    }

    /*Sets the reset code for this password reset token. */
    public void setCode(String code){
        this.code=code;
    }

    /*Sets the expiration timestamp of this password reset token. */
    public void setExpirationTime(LocalDateTime expirationTime){
        this.expirationTime = expirationTime;
    }





}
