package com.shiro.Repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shiro.Entity.Submission;
import com.shiro.Entity.Task;

public interface SubmissionRepo extends CrudRepository<Submission, Integer>{
    Optional<Submission> findByTask(Task task);
}
