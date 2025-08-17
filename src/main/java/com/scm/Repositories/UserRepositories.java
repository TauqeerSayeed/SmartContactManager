package com.scm.Repositories;

import com.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User,String> {
    //exgtra methods db releated operation
    //custom query methods
    //custom finder methods

    Optional<User> findByEmailId(String emailId);
    Optional<User> findByEmailIdAndPassword(String emailId,String password);
    Optional<User> findByEmailToken(String id);
}
