package com.scm.Controllers;

import com.scm.Forms.contactForm;
import com.scm.Forms.contactSearchForm;
import com.scm.Services.contactService;
import com.scm.Services.imageService;
import com.scm.Services.userServices;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger= LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private imageService imageService1;

    @Autowired
    private contactService contactService1;

    @Autowired
    private userServices userServices1;

    //add contact handelr
    @RequestMapping("/add")
    public String addContactView(Model model){
        contactForm contactForm = new contactForm();
//        contactForm.setName("maeinu Khan");
        contactForm.setFavorite(true);
        model.addAttribute("contactForm",contactForm);
        return "User/add_contact";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute contactForm contactForm1, BindingResult result,
                              Authentication authentication, HttpSession session){
        //process the form data

        //validate the form
        if(result.hasErrors()){
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors!")
                            .type(MessageType.red)
                    .build());
            return "User/add_contact";
        }



        String username= Helper.getEmailOfLoggedInUser(authentication);
        //form----> contact

        User user = userServices1.getUserByEmailId(username);
        //process the contact picture
        //file kp upload krne ka code
//        String filename= UUID.randomUUID().toString();
//        String fileURL=imageService1.uploadImage(contactForm1.getContactImage(),filename);

        // uplod karne ka code
        Contact contact=new Contact();

        contact.setName(contactForm1.getName());
        contact.setFavorite(contactForm1.isFavorite());
        contact.setEmail(contactForm1.getEmail());
        contact.setAddress(contactForm1.getAddress());
        contact.setDescription(contactForm1.getDescription());
        contact.setPhoneNumber(contactForm1.getPhoneNumber());
        contact.setWebsiteLink(contactForm1.getWebsiteLink());
        contact.setLinkedInLink(contactForm1.getLinkedInLink());
        contact.setUser(user);

        if (contactForm1.getContactImage() != null && !contactForm1.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService1.uploadImage(contactForm1.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
//        contact.setPicture(fileURL);
//        contact.setCloudinaryImagePublicId(filename);
        contactService1.save(contact);
        System.out.println(contactForm1);
        //set the contact picture url

        //set msg to dislpay on view
        session.setAttribute("message",Message.builder()
                .content("Your have succeffully added a new contact")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/add";
    }



    //view contacts

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,Authentication authentication){

        //load all the user contact
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices1.getUserByEmailId(username);
        Page<Contact> pageContact = contactService1.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("pageContact",pageContact);

        model.addAttribute("contactSearchForm", new contactSearchForm());
        return "User/contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute contactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+ "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication
    ){

        logger.info("field {} keyword {}",contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userServices1.getUserByEmailId(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact>  pageContact=null;

        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact=contactService1.searchByName(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact=contactService1.searchByEmail(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("phone")){
            pageContact=contactService1.searchByPhoneNumber(contactSearchForm.getValue(),size,page,sortBy,direction,user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "User/search";
    }


    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService1.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/user/contacts";
    }


    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService1.getById(contactId);

        contactForm contactForm = new contactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }


    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid @ModelAttribute contactForm contactForm,
                                BindingResult bindingResult,
                                Model model,HttpSession session) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService1.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService1.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            logger.info("file is empty");
        }

        var updateCon = contactService1.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        session.setAttribute("message",Message.builder()
                .content("Your have succeffully updated this contact")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/view/" + contactId;
    }

}