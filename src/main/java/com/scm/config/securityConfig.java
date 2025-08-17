package com.scm.config;

import com.scm.Services.Impl.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("admin123")
//                .password("admin123")
//                .roles("ADMIN","USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("user123")
//                .password("user123")
//                .build();
//
//        var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1,user2);
//        return inMemoryUserDetailsManager;
//    }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private AuthFailureHandler authFailureHandler;


    //configuratuon of authentoication provider spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user detail service ka obj
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder ka obj
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //configuration
        //yaha urls jo configure kia h k kon se public rhenge or kon private 
        httpSecurity.authorizeHttpRequests(authorize -> {
//            authorize.requestMatchers("/home","/register","services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default login
        //agar hme kux bhi change krna h from releated to hm yaha aayenge
        httpSecurity.formLogin(formLogin->{

            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
//            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("emailId");
            formLogin.passwordParameter("password");


            formLogin.failureHandler(authFailureHandler);

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth2 client configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
