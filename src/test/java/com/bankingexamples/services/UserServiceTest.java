package com.bankingexamples.services;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

public class UserServiceTest {

    UserDAO ud;
    UserService us;

    @Before
    public void testSetup(){
        ud = Mockito.mock(UserDAO.class);
        us = new UserService(ud);
    }

    @Test
    public void testFindUserById() {

        Role r = new Role(1, "Admin");
        User u = new User(101, "aa", "aa", "aa", "aa", "aa@a.com", r);

        Mockito.when(ud.getUserById(101)).thenReturn(u);

//        assertEquals(101, u.getUserId());

    }
}


