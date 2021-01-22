package com.codewithshihab.server.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback implements Serializable {
    private String description;
    private User givenBy;
}
