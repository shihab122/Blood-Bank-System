package com.codewithshihab.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class BloodRequest implements Serializable {
    @Id
    private String id;

    private User requestBy;

    private String relationshipWithPatient;

    private String requestedBloodGroup;

    private String alternateMobileNumber;

    private TimeFrame timeFrame;

    private LocalDateTime donationTime;

    private List<User> interestedDonorList;

    private List<DonationInformation> donationInformationList;

    private List<Feedback> feedbackList;
}
