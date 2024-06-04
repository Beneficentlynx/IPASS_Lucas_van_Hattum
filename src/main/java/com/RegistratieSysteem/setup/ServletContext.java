package com.RegistratieSysteem.setup;

import com.RegistratieSysteem.model.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.time.Duration;

@WebListener
public class ServletContext implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){
        User.setAllUsers(User.readUser());
//        User adminuser = new User("admin", "admin", "TEST");
//        User.addUserToAll(adminuser);
        System.out.println(User.getAllUsers());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            User.writeUser(User.getAllUsers());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
