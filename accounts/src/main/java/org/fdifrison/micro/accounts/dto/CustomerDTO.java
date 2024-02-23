package org.fdifrison.micro.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min= 5, max = 30, message = "The length of customer's name must be between 5 and 30")
    private String name;
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Not a valid email format")
    private String email;
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;
    private AccountDTO accountDTO;
}
