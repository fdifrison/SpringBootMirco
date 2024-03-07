package org.fdifrison.micro.accounts.service.client;

import org.fdifrison.micro.accounts.dto.external.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Primary
@FeignClient(name = "cards", fallback = CardsFallBack.class)
public interface CardsFeignClient {

    @GetMapping(value = "api/fetch", consumes = "application/json")
    ResponseEntity<CardDTO> fetchCardDetails(@RequestHeader String correlationId, @RequestParam String mobileNumber);

}

