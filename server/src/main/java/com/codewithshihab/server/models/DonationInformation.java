package com.codewithshihab.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationInformation implements Serializable {
    private User donateBy;
    private LocalDateTime donateOn;
}
