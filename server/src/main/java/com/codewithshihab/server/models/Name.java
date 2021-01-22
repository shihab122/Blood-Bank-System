package com.codewithshihab.server.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Name {
    /**
     * Any Salutation like Mr Ms Msc Dr
     */
    @Pattern(
            regexp = "^[A-Za-z\\s\\-\\.]+$",
            message = "Salutation has invalid character. Allowed characters are A-Z a-z - ( ).")
    @Size(
            min = 2,
            max = 50,
            message = "Salutation must be between 2 and 50 characters long"
    )
    private String salutationType;

    /**
     * First Name of a person
     */
    @NotBlank(message = "First name can't be blank")
    @Pattern(
            regexp = "^[A-Za-z\\s\\-\\.]+$",
            message = "First name has invalid character. Allowed characters are A-Z a-z - ( ).")
    @Size(
            min = 2,
            max = 100,
            message = "First name must be between 2 and 100 characters long"
    )
    private String firstName;

    /**
     * Middle Name of a person
     */
    @Pattern(
            regexp = "^[A-Za-z\\s\\-\\.]+$",
            message = "Middle name has invalid character. Allowed characters are A-Z a-z - ( ).")
    @Size(
            min = 2,
            max = 100,
            message = "Middle name must be between 2 and 100 characters long"
    )
    private String middleName;

    /**
     * Last Name of a person
     */
    @Pattern(
            regexp = "^[A-Za-z\\s\\-\\.]+$",
            message = "Last name has invalid character. Allowed characters are A-Z a-z - ( ).")
    @Size(
            min = 2,
            max = 100,
            message = "Last name must be between 2 and 100 characters long"
    )
    private String lastName;

    /**
     * Suffix of a person like Sr. Jr.
     */
    @Pattern(
            regexp = "^[A-Za-z\\s\\-\\.]+$",
            message = "Suffix has invalid character. Allowed characters are A-Z a-z - ( ).")
    @Size(
            min = 2,
            max = 100,
            message = "Suffix must be between 2 and 100 characters long"
    )
    private String suffix;

    public enum SalutationType {
        MR("Mr."),
        MS("Ms."),
        MRS("Mrs."),
        DR("Dr.");

        /**
         * Salutation Type of a name
         */
        private String name;

        SalutationType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
