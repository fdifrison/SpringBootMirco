package org.fdifrison.micro.accounts.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.fdifrison.micro.accounts.dto.ErrorResponseDTO;
import org.fdifrison.micro.accounts.dto.external.CustomerDetailsDto;
import org.fdifrison.micro.accounts.service.ICustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "REST APIs for Customers",
        description = "Fetch integral customers details"
)
public class CustomerController {

    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }


    @Operation(
            summary = "Fetch Customer details",
            description = "Fetch a customer with the associated account, loans and cards given mobile number")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("fdifrison-correlation-id")
                                                                   String correlationId,
                                                                   @RequestParam
                                                                   @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                                   String mobileNumber) {
        logger.debug("fdifrison-correlation-id found: {}", correlationId);
        var customerDetails = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetails);

    }

}
