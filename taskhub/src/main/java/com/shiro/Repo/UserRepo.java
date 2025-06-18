package com.shiro.Repo;
import org.springframework.data.repository.CrudRepository;

import com.shiro.Entity.User;

public interface  UserRepo extends CrudRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    Iterable<User> findByRole(String role);
}
