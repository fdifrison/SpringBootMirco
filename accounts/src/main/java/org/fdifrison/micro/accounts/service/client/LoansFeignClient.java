package org.fdifrison.micro.accounts.service.client;

import org.fdifrison.micro.accounts.dto.external.LoanDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallBack.class)
public interface LoansFeignClient {

    @GetMapping(value = "api/fetch", consumes = "application/json")
    ResponseEntity<LoanDTO> fetchLoansDetails(@RequestHeader String correlationId, @RequestParam String mobileNumber);

}
