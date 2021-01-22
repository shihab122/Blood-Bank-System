package com.codewithshihab.server.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(of = {"code"})
public class Country implements Serializable {

    @Id
    private String id;

    /**
     * Country code of Country like (BD, AU)
     */
    @Indexed(unique = true)
    @NotBlank(message = "Country code can't be blank")
    @Size(min = 2, max = 3, message = "Country code must be between 2 and 3 characters")
    @Pattern(regexp = "^[A-Z]*$", message = "Country code does not match the pattern.")
    private String code;

    /**
     * Full Name of a country
     */
    @Indexed(unique = true)
    @NotBlank(message = "Country name can't be blank")
    @Size(min = 4, max = 40, message = "Country name must be between 4 and 40 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]*$", message = "Country name does not match the pattern.")
    private String name;
}

