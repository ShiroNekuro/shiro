package com.shiro.Repo;

import org.springframework.data.repository.CrudRepository;

import com.shiro.Entity.Task;

public interface  TaskRepo extends CrudRepository<Task, Integer> {
    
}
