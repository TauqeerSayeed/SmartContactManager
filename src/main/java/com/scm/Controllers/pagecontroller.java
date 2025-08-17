package com.scm.Controllers;

import com.scm.Forms.userForm;
import com.scm.Services.userServices;
import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class pagecontroller {

    @Autowired
    private userServices userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name","substring technology");
        model.addAttribute("youtube","codeverseMainu");
        model.addAttribute("github","https://github.com/Maeinukhan63");
        return "HOME";
    }

    //about page
    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin",true);
        return "about";
    }

    //services
    @RequestMapping("/services")
    public String aboutServices(){

        return "services";
    }

    @GetMapping("/contact")
    public String contact(){

        return new String("contact");
    }

    //this is showing login page
    @GetMapping("/login")
    public String login(){

        return new String("login");
    }

    //registration page
    @GetMapping("/register")
    public String register(Model model){

        userForm userForm=new userForm();
//        userForm.setName("Maeinuddin");
//        userForm.setAbout("hii guys!!!");
        model.addAttribute("userForm",userForm);

        return "Register";
    }

    //processing register

    @RequestMapping(value = "/do-register" ,method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute userForm userForm, BindingResult rbindingResult, HttpSession session){
        //fetch form data
        //Userform
        System.out.println(userForm);
        //validate form data
        if(rbindingResult.hasErrors()){
            return "register";
        }


        //save to db
        //userForm -->user
//        User user = User.builder()
//                .name(userForm.getName())
//                .emailId(userForm.getEmailId())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .phoneNumber(userForm.getPhoneNumber())
////                .profilePic("")
//                .build();
        User user=new User();
        user.setName(userForm.getName());
        user.setEmailId(userForm.getEmailId());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75");

        User savedUser = userService.saveUser(user);
        System.out.println("user saved");
        //message="registrating successfuk
        //add message

        Message message= Message.builder().content("Registraion Successful").type(MessageType.green).build();
        session.setAttribute("message",message);

        //redirect to login page
        return "redirect:/register";
    }
}
