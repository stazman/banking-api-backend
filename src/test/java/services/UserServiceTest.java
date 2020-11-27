package services;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import com.bankingexamples.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

public class UserServiceTest {

    UserDAO ud;
    UserService us;

    private final Logger logger = Logger.getLogger(String.valueOf(UserServiceTest.class));

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

        logger.info("The findUserById() service method test has been run.");
    }
}




