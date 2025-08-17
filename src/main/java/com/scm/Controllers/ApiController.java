package com.scm.Controllers;

import com.scm.Services.contactService;
import com.scm.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    contactService contactService1;
    // get contact



    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        return contactService1.getById(contactId);
    }
}
