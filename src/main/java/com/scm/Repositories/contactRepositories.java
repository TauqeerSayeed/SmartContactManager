package com.scm.Repositories;

import com.scm.entities.Contact;
import com.scm.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contactRepositories extends JpaRepository<Contact,String> {
    //find contact by user

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("Select c from Contact c where c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);
    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);
}
