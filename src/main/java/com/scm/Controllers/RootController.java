package com.scm.Controllers;

import com.scm.Services.userServices;
import com.scm.entities.User;
import com.scm.helper.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private userServices userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }
        System.out.println("adding logged in user information to the model");
        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}",username);

        //user data ko fetch kr skte h DB se
        User user = userService.getUserByEmailId(username);
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getEmailId());
        model.addAttribute("loggedInUser",user);
    }
}
