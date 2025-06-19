package com.shiro.Repo;

import java.util.Optional;
import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shiro.Entity.Task;
import com.shiro.Entity.User;

public interface  TaskRepo extends CrudRepository<Task, Integer> {
    Iterable<Task> findByAssignee(Optional<User> userid);

    List<Task> findByDeadlineBetween(Date start, Date end);
}

