package org.fdifrison.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountDTO {
    @Schema(description = "Account number", example = "1123456789")
    @NotEmpty(message = "Account number cannot be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digit")
    private Long accountNumber;
    @Schema(description = "Branch type", example = "Savings")
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;
    @Schema(description = "Branch address", example = "0, Via dei Matti")
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;
}
