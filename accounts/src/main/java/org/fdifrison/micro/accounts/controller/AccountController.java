package org.fdifrison.micro.accounts.controller;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.fdifrison.micro.accounts.config.AppContactInfo;
import org.fdifrison.micro.accounts.constants.AccountConstants;
import org.fdifrison.micro.accounts.dto.CustomerDTO;
import org.fdifrison.micro.accounts.dto.ErrorResponseDTO;
import org.fdifrison.micro.accounts.dto.ResponseDTO;
import org.fdifrison.micro.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "Create, Update, Fetch and Delete accounts details"
)
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final IAccountService service;
    private final Environment environment;
    private final AppContactInfo contactInfo;
    @Value("${build.version}")
    private String buildVersion;

    public AccountController(IAccountService service, Environment environment, AppContactInfo contactInfo) {
        this.service = service;
        this.environment = environment;
        this.contactInfo = contactInfo;
    }


    @Operation(
            summary = "Create account",
            description = "Create a customer with the associated account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@Valid @RequestBody CustomerDTO customerDTO) {

        service.creatAccount(customerDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch account",
            description = "Fetch a customer with the associated account given mobile number")
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
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                           String mobileNumber) {
        var customerDTO = service.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @Operation(
            summary = "Updated account",
            description = "Update a customer with the associated account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountDetails(@Valid @RequestBody CustomerDTO customerDTO) {
        var idUpdated = service.updateAccount(customerDTO);
        if (idUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete account",
            description = "Delete a customer with the associated account")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
                                                     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digit")
                                                     String mobileNumber) {
        var isDeleted = service.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get Build Info",
            description = "Get Build information of the currently deployed account microservice")
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
    @Retry(name = "getBuildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        logger.debug("getBuildInfo invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

        // we need the same signature in the fallback method + a Throwable
    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        logger.debug("getBuildInfoFallback invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("fall-back-build-info");
    }

    @Operation(
            summary = "Get Java Info",
            description = "Get Java information of the currently deployed account microservice")
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
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Get Contact information to be reached for any issue")
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
    @GetMapping("/contact-info")
    public ResponseEntity<AppContactInfo> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactInfo);
    }


}
