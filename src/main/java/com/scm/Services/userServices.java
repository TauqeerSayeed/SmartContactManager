package com.scm.Services;

import com.scm.entities.User;

import java.util.List;
import java.util.Optional;

public interface userServices {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUsereExistByEmailId(String emailId);

    List<User> getAllUsers();

    User getUserByEmailId(String emailId);

    //add mmore methods releated to user service
}
