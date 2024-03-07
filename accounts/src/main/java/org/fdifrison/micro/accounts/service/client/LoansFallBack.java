package org.fdifrison.micro.accounts.service.client;

import org.fdifrison.micro.accounts.dto.external.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBack implements LoansFeignClient{
    @Override
    public ResponseEntity<LoanDTO> fetchLoansDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
