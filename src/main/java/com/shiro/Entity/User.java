package com.shiro.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int iduser;

    @Column(length = 100)
    private String resetToken;

    private LocalDateTime tokenExpiry;

    @Column(length=80)
    private String name;

    @Column(length=32)
    private String username;

    @Column(length=20)
    private String password;

    @Column(length=20)
    private String role;

    public User(){
    }

    public Integer getId(){
        return this.iduser;
    }

    public void setId(int id){
        this.iduser = id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String uname){
        this.username = uname;
    } 

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String pw){
        this.password = pw;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }

    public LocalDateTime getTokenExpiry() { return tokenExpiry; }
    public void setTokenExpiry(LocalDateTime tokenExpiry) { this.tokenExpiry = tokenExpiry; }
}
