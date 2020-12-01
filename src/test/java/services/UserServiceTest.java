package services;

import com.bankingexamples.dao.UserDAO;
import com.bankingexamples.models.Role;
import com.bankingexamples.models.User;
import com.bankingexamples.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

//import java.util.logging.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceTest {

    UserDAO ud;
    UserService us;

//    private final Logger logger = Logger.getLogger(String.valueOf(UserServiceTest.class));
    private final Logger logger = LoggerFactory.getLogger(String.valueOf(UserServiceTest.class));

    String findById = "testFindUserById()";

    @Before
    public void testSetup(){
        ud = Mockito.mock(UserDAO.class);
        us = new UserService(ud);
    }

    @Test
    public void testFindUserById() {

        Role r = new Role(1, "Admin");
        User u = new User(230, "aa", "aa", "aa", "aa", "aa@a.com", r);

        Mockito.when(ud.getUserById(230)).thenReturn(u);

        logger.info("The {} service method test has been run.", findById);
    }
}




