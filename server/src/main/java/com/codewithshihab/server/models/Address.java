package com.codewithshihab.server.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"addressType", "streetAddress", "policeStation", "postOffice", "subDistrict", "district", "division", "country"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable {

    @Id
    private String id;

    /**
     * Floor number, House number, Road number, Section, Area of address
     */
    @Size(min = 10, max = 100, message= "Street address must be between 10 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Street address does not match the pattern")
    private String streetAddress;

    /**
     * Division name depends on the country
     */
    @Size(min = 5, max = 100, message= "Division must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Division does not match the pattern")
    private String division;

    /**
     * District name depends
     */
    @Size(min = 2, max = 100, message= "District must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "District does not match the pattern")
    private String district;

    @Size(min = 2, max = 100, message= "Union must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Union does not match the pattern")
    private String union;

    /**
     * PoliceStation name depends on the SubDistrict
     */
    @Size(min = 2, max = 100, message= "Police station must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Police station does not match the pattern")
    private String policeStation;

    /**
     * PostOffice depends on the SubDistrict
     */
    @Size(min = 2, max = 100, message= "Post office must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Post office does not match the pattern")
    private String postOffice;

    /**
     * PostOffice depends on the SubDistrict
     */
    @Size(min = 2, max = 100, message= "Post code must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.,\\s-]*$", message = "Post code does not match the pattern")
    private String postCode;

    /**
     * Country of the address
     */
    private Country country;

}

