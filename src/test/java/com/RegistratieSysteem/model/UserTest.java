package com.RegistratieSysteem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User u1;
    User u2;

    @BeforeEach
    void setUp() {
        u1 = new User("test1", "test1", "I2");
        u2 = new User("test2", "test2", "I2");
        User.addUserToAll(u1);
        User.addUserToAll(u2);
    }

    @Test
    void getUserByNameCorrectName() {
        assertEquals((u1.toString()), (User.getUserByName("test1").toString()));
    }

    @Test
    void getUserByNameFalseName() {
        assertNull(User.getUserByName("this name doesnt exist"));
    }


    @Test
    void checkLoginCorrect() {
        assertEquals(true, User.checkLogin("test2", "test2"));

    }

    @Test
    void checkLoginFalse() {
        assertEquals(false, User.checkLogin("test1", "this isnt the password of test1"));
    }
}