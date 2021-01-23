package com.codewithshihab.server;

import com.codewithshihab.server.exception.ExecutionFailureException;
import com.codewithshihab.server.models.*;
import com.codewithshihab.server.service.BloodRequestService;
import com.codewithshihab.server.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BloodRequestServiceTest {
    @Autowired
    private BloodRequestService bloodRequestService;
    @Autowired
    private UserService userService;

    public String requestId;

    @Before
    public void createRequest() throws ExecutionFailureException {
        createUser();
        BloodRequest bloodRequest = new BloodRequest();
        bloodRequest.setRequestedBloodGroup("A+");
        bloodRequest.setRelationshipWithPatient("Brother");
        bloodRequest.setTimeFrame(new TimeFrame(LocalDateTime.now(), LocalDateTime.parse("2021-02-21T23:59:5")));
        requestId = bloodRequestService.create(bloodRequest, userService.login(new LoginRequestBody("01923678911", "123456", false))).getId();
    }

    @Test
    public void findById() throws ExecutionFailureException {
        BloodRequest bloodRequest = bloodRequestService.findById(requestId);
        assertEquals("01923678911", bloodRequest.getRequestBy().getUsername());
        assertEquals("A+", bloodRequest.getRequestedBloodGroup());
        assertEquals("Brother", bloodRequest.getRelationshipWithPatient());
    }

    @After
    public void deleteRequest() {
        bloodRequestService.deleteRequest(requestId);
    }

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

}
