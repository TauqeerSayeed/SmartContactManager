package com.scm.Controllers;

import com.scm.Services.userServices;
import com.scm.entities.User;
import com.scm.helper.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    //user dashboard

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private userServices userService;




    @RequestMapping(value = "/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

    //user profile poage

    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication){
        return "user/profile";
    }

    //user add contact page


    //user view page

    //user edit contact


    //user delete contact
}
