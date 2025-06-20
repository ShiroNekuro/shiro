package com.shiro.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiro.Entity.Submission;
import com.shiro.Entity.Task;
import com.shiro.Repo.SubmissionRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SubmissionService {
    @Autowired
    private SubmissionRepo repo;

    public void addSubmission(Submission submission){
        repo.save(submission);
    }

    public void updateSubmission(Submission submission){
        repo.save(submission);
    }

    public Iterable<Submission> findAll(){
        return repo.findAll();
    }

    public void deleteSubmission(Integer id){
        repo.deleteById(id);
    }

    public Optional<Submission> findByTask(Task task){
        return repo.findByTask(task);
    }

    public Optional<Submission> findById(Integer id){
        return repo.findById(id);
    }
}
