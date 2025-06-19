package com.shiro.Repo;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import com.shiro.Entity.User;

public interface  UserRepo extends CrudRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    Optional<User> findByResetToken(String token);
    Iterable<User> findByRole(String role);
}
