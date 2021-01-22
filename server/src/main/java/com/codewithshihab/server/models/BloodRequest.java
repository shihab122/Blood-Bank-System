package com.codewithshihab.server.models;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class BloodRequest implements Serializable {
    @Id
    private String id;

    private User requestBy;

    private String relationshipWithPatient;

    private String alternateMobileNumber;

    private LocalDateTime timeFrame;

    private List<User> acceptBy;

    private List<User> donateBy;

    private LocalDateTime donateOn;

    private List<Feedback> feedbackList;
}
