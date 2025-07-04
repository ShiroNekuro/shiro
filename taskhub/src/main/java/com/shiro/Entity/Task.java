package com.shiro.Entity;
import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idtask;

    @Column(length=200)
    private String name;
    
    @Column(name = "description")
    private String description;

    @Column(name = "deadline")
    private Date deadline;

    
    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="assignee")
    private User assignee;

    @Column(name = "isDone", nullable=false, columnDefinition="bit(1) DEFAULT 0")
    private Boolean isDone;


    // Setter and Getter
    public Integer getId(){
        return this.idtask;
    }

    public void setId(Integer id){
        this.idtask = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDesc(){
        return this.description;
    }

    public void setDesc(String desc){
        this.description = desc;
    }

    public Date getDeadline(){
        return this.deadline;
    }

    public void setDeadline(Date date){
        this.deadline = date;
    }

    public User getAssignee(){
        return this.assignee;
    }

    public void setAssignee(User user){
        this.assignee = user;
    }

    public Boolean getIsDone(){
        return this.isDone;
    }

    public void setIsDone(Boolean status){
        this.isDone = status;
    }
}
