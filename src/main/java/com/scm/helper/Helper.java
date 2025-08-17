package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){


        //agar hmne email se login kia h to email kse nikalenge
        if(authentication instanceof OAuth2AuthenticationToken) {

           var OAuth2AuthenticationToken  = (OAuth2AuthenticationToken) authentication;
           var clientId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oAuth2User = (OAuth2User) authentication.getPrincipal();
            String username="";

            if(clientId.equalsIgnoreCase("google")) {
                //sign with google se kse find krenge
                System.out.println("getting email with google");
                username=oAuth2User.getAttribute("email").toString();
            }
            else if(clientId.equalsIgnoreCase("github")) {
                //sign with github se kse find krenge
                System.out.println("getting email with github");
                username=oAuth2User.getAttribute("email") !=null ? oAuth2User.getAttribute("email").toString()
                        :oAuth2User.getAttribute("login").toString() + "@gmail.com";

            }
            return username;
        }else{
            System.out.println("getting data from local DB");
            return authentication.getName();
        }

    }


    public  static String getLinkForEmailVerificatiton(String emailToken) {

        //ye to sirf error hatane k liye returnkia h kaam to ismebaaki h

        String link= "http://localhost:8080/auth/verify-email?token="+emailToken;
       return link;

    }
}
