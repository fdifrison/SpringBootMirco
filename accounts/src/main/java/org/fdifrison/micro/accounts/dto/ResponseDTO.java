package org.fdifrison.micro.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold Successful response information"
)
public class ResponseDTO {

    @Schema(description = "Status code in response", example = "200")
    private String statusCode;
    @Schema(description = "Status message in response", example = "Request processed successfully")
    private String statusMessage;

}
