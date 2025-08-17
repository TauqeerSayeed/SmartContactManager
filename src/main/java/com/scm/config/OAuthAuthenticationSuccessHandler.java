package com.scm.config;

import com.scm.Repositories.UserRepositories;
import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger= LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepositories userRepositories;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");
        //google login

        var oAuth2AuthenticationToken  = (OAuth2AuthenticationToken)authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        oauthUser.getAttributes().forEach((key,value)->{
            logger.info(key+" : "+value);
        });

        User user =new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");
        //github login
        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){

            user.setEmailId(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProvoderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");


        }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){

            String email=oauthUser.getAttribute("email") !=null ? oauthUser.getAttribute("email").toString()
                    :oauthUser.getAttribute("login").toString() + "@gmail.com";

            String picture=oauthUser.getAttribute("avatar_url").toString();
            String name=oauthUser.getAttribute("login").toString();
            String providerUserId=oauthUser.getName();

            user.setEmailId(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProvoderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github.");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {



        }else{
            logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
        }


        //facebook login



        /*
        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();


//        logger.info(user.getName());
//        user.getAttributes().forEach((key,value)->{
//            logger.info("{} => {}",key,value);
//        });
//
//        logger.info(user.getAuthorities().toString());

        //dbsave user
        String email=user.getAttribute("email").toString();
        String name=user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();

        //crreate user and save it
        User user1=new User();
        user1.setEmailId(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Providers.GOOGLE);
        user1.setEmailVerified(true);
        user1.setEnabled(true);
        user1.setProvoderUserId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("This account is created using google...");

        User user2 = userRepositories.findByEmailId(email).orElse(null);
        if(user2==null){
            userRepositories.save(user1);
            logger.info("User saved: "+email);
        }

*/

        User user2 = userRepositories.findByEmailId(user.getEmailId()).orElse(null);
        if(user2==null){
            userRepositories.save(user);
        }
        new DefaultRedirectStrategy().sendRedirect(request, response,"/user/profile");

    }
}
