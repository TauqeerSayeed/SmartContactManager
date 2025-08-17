package com.scm.Services.Impl;

import com.scm.Repositories.UserRepositories;
import com.scm.Services.EmailService;
import com.scm.Services.userServices;
import com.scm.entities.User;

import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements userServices {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private  Helper helper;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //userId : have to generarte
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);

        //password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());


        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepositories.save(user);
        String emailLink = Helper.getLinkForEmailVerificatiton(emailToken);
        emailService.sendEmail(savedUser.getEmailId(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepositories.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User user2= userRepositories.findById(user.getUserId())
               .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        //update user2 from user
        user2.setName(user.getName());
        user2.setEmailId(user.getEmailId());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProvoderUserId(user.getProvoderUserId());

        //save the user2 in DB
        User save = userRepositories.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user= userRepositories.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepositories.delete(user);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2= userRepositories.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUsereExistByEmailId(String emailId) {
        User user = userRepositories.findByEmailId(emailId).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositories.findAll();
    }

    @Override
    public User getUserByEmailId(String emailId) {
        return userRepositories.findByEmailId(emailId).orElse(null);
    }
}
