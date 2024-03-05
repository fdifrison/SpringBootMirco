package org.fdifrison.micro.accounts.dto.external;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.fdifrison.micro.accounts.dto.AccountDTO;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {

    @Schema(description = "Name of the customer", example = "G. Frison")
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of customer's name must be between 5 and 30")
    private String name;
    @Schema(description = "Email address of the customer", example = "customer@provider.com")
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Not a valid email format")
    private String email;
    @Schema(description = "Mobile number of the customer", example = "0123456789")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
    private String mobileNumber;
    @Schema(description = "Account details of the customer")
    private AccountDTO accountDTO;
    @Schema(description = "Loans details of the customer")
    private LoanDTO loanDTO;
    @Schema(description = "Cards details of the customer")
    private CardDTO cardDTO;

}
