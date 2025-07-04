package com.shiro.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiro.Entity.Task;
import com.shiro.Entity.User;
import com.shiro.Repo.TaskRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepo repo;
    
    public Optional<Task> findById(Integer id){
        return repo.findById(id);
    }

    public Iterable<Task> findAll(){
        return repo.findAll();
    }

    public Iterable<Task> findByAssignee(Optional<User> userid){
        return repo.findByAssignee(userid);
    }

    public void addTask(Task task){
        repo.save(task);
    }

    public void deleteTask(int id){
        repo.deleteById(id);
    }

    public void updateTask(Task task){
        repo.save(task);
    }

    public Iterable<Task> findByAssigneeAndIsDone(Optional<User> user, Boolean IsDone){
        return repo.findByAssigneeAndIsDone(user, IsDone);
    }

    
    public Integer countByAssigneeAndIsDone(User user, Boolean isDone){
        return repo.countByAssigneeAndIsDone(user,isDone);
    }
}
