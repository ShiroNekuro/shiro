package com.shiro.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String filename;
    private String contentType;

    @Lob
    @Column(length = 10485760)
    private byte[] data;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="task")
    private Task task;

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getFilename(){
        return this.filename;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getContentType(){
        return this.contentType;
    }

    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public byte[] getData(){
        return this.data;
    }

    public void setData(byte[] data){
        this.data = data;
    }

    public Task getTask(){
        return this.task;
    }

    public void setTask(Task task){
        this.task = task;
    }
}
