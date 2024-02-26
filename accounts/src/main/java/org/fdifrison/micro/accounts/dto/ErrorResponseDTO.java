package org.fdifrison.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDTO {
    @Schema(description = "API path invoked by client")
    private String apiPath;
    @Schema(description = "Error code", example = "401")
    private HttpStatus errorCode;
    @Schema(description = "Error message", example = "Unauthorized")
    private String errorMessage;
    @Schema(description = "Time of occurrence of the error")
    private LocalDateTime errorTime;
    @Schema(description = "List of validation error on DTO schemas")
    private Map<String, String> validationErrors;

    public ErrorResponseDTO(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }
}
