package com.codewithshihab.server;

import com.codewithshihab.server.exception.ExecutionFailureException;
import com.codewithshihab.server.models.*;
import com.codewithshihab.server.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Before
    public void createUser() throws ExecutionFailureException {
        User user = new User();
        user.setName(new Name());
        user.getName().setFirstName("Ariful");
        user.getName().setLastName("Islam");
        user.setPassword("123456");
        user.setType(UserType.GENERAL);
        user.setEmail("arifulislam@gmail.com");
        user.setMobileNumber("01923678911");
        user.setAlternateMobileNumber("01997157535");
        user.setBloodGroup("A+");
        user.setWeight(65.8);
        user.setDateOfBirth(LocalDate.parse("1999-10-13"));
        user.setReligion("Islam");
        user.setPresentAddress(new Address());
        user.getPresentAddress().setUnion("Mohanpur");
        user.getPresentAddress().setPostOffice("Mohanpur");
        user.getPresentAddress().setPostCode("1004");
        user.getPresentAddress().setPoliceStation("Matlab North");
        user.getPresentAddress().setDistrict("Chandpur");
        userService.save(user);

    }

    @Test
    public void findByUsername() throws ExecutionFailureException {
        Name name = new Name();
        name.setFirstName("Ariful");
        name.setLastName("Islam");

        Address address = new Address();
        address.setUnion("Mohanpur");
        address.setPostOffice("Mohanpur");
        address.setPostCode("1004");
        address.setPoliceStation("Matlab North");
        address.setDistrict("Chandpur");

        User user = userService.getByUsername("01923678911");
        assertEquals(name, user.getName());
        assertEquals(UserType.GENERAL, user.getType());
        assertEquals("arifulislam@gmail.com", user.getEmail());
        assertEquals("01923678911", user.getUsername());
        assertEquals("01923678911", user.getMobileNumber());
        assertEquals("01997157535", user.getAlternateMobileNumber());
        assertEquals("A+", user.getBloodGroup());
        assertEquals(65.8, user.getWeight());
        assertEquals(LocalDate.parse("1999-10-13"), user.getDateOfBirth());
        assertEquals(LocalDate.parse("1999-10-13"), user.getDateOfBirth());
        assertEquals("Islam", user.getReligion());
        assertEquals(address, user.getPresentAddress());
    }

    @Test
    public void loginTest() throws ExecutionFailureException {
        LoginRequestBody loginRequestBody = new LoginRequestBody("01923678911", "123456", false);
        assertEquals("01923678911", userService.getUsernameFromAccessToken(userService.login(loginRequestBody)));
    }

    @After
    public void deleteUser() {
        userService.deleteByUsername("01923678911");
    }

}
