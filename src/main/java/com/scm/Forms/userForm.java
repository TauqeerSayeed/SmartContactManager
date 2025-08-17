package com.scm.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class userForm {

    @NotBlank(message = "Username is required!")
    @Size(min = 3, message = "Min 3 Character is required!")
    private String name;

    @Email(message = "Invalid Email Address!")
    @NotBlank(message = "Email is required!")
    private String emailId;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Min 6 Character is required!")
    private String password;

    @NotBlank(message = "About is required!")
    private String about;

    @Size(min = 8, max = 12, message = "Invalid Phone Number!")
    private String phoneNumber;
}
