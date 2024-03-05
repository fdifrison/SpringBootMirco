package org.fdifrison.micro.accounts.service.client;

import org.fdifrison.micro.accounts.dto.external.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface CardsFeignClient {

    @GetMapping(value = "api/fetch", consumes = "application/json")
    ResponseEntity<CardDTO> fetchCardDetails(@RequestParam String mobileNumber);

}
