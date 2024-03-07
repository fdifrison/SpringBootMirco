package org.fdifrison.micro.accounts.service.client;

import org.fdifrison.micro.accounts.dto.external.CardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallBack implements CardsFeignClient {
    @Override
    public ResponseEntity<CardDTO> fetchCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
