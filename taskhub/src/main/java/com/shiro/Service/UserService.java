package com.shiro.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiro.Entity.User;
import com.shiro.Repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public Iterable<User> findAlluser(){
        return repo.findAll();
    }

    public void addUser(User user){
        repo.save(user);
    }

    public void deleteUser(int id){
        repo.deleteById(id);
    }

    public Optional<User> findById(int id){
        return repo.findById(id);
    }

    public void updateUser(User user){
        repo.save(user);
    }

    public User findByUsernameAndPassword(String username, String password){
        return repo.findByUsernameAndPassword(username, password);
    }

    public User findByUsername(String username){
        return repo.findByUsername(username);
    }
    public Iterable<User> findByRole(String role){
        return repo.findByRole(role);
    }
}
