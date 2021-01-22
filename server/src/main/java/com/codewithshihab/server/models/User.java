package com.codewithshihab.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private Name name;

    @NotNull(message = "User type can't blank. It can only ADMIN or GENERAL")
    private UserType type;

    private String email;

    private String mobileNumber;

    private String alternateMobileNumber;

    private String bloodGroup;

    // Weight must be in kg
    private double weight;

    private String dateOfBirth;

    private String religion;

    private Address presentAddress;

    private LocalDateTime availableFrom;

    private LocalDateTime createdAt;

    // Common attributes for all model class
    @Indexed
    private List<ActivityFeed> activityFeedList;
}
