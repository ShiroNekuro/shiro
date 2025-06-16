package com.shiro.Entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idtask;

    @Column(length=200)
    private String name;

    private String description;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="iduser")
    private User assignee;

    public Integer getId(){
        return this.idtask;
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

    public User getAssignee(){
        return this.assignee;
    }
}
