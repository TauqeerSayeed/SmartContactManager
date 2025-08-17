package com.scm.Services;

import com.scm.entities.Contact;
import com.scm.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface contactService {
    //save contct
    Contact save(Contact contact);

    //update
    Contact update(Contact contact);

    //get
    List<Contact> getAll();

    //get content by id
    Contact getById(String id);

    //delete contect
    void delete(String id);

    //search contect
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);


    //get contect by user id
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user, int page,int size,String sortField,String sortDirection);


}
