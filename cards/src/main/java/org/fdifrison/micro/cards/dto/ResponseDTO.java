package org.fdifrison.micro.cards.dto;

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

    @Schema(description = "Status code in response")
    private String statusCode;
    @Schema(description = "Status message in response")
    private String statusMessage;

}
