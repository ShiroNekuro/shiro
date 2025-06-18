package com.shiro.Repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.shiro.Entity.Task;
import com.shiro.Entity.User;

public interface  TaskRepo extends CrudRepository<Task, Integer> {
    Iterable<Task> findByAssignee(Optional<User> userid);
}
