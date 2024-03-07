package org.fdifrison.micro.accounts.service.impl;

import org.fdifrison.micro.accounts.dto.external.CustomerDetailsDto;
import org.fdifrison.micro.accounts.exception.ResourceNotFoundException;
import org.fdifrison.micro.accounts.mapper.AccountMapper;
import org.fdifrison.micro.accounts.mapper.CustomerMapper;
import org.fdifrison.micro.accounts.repository.AccountRepository;
import org.fdifrison.micro.accounts.repository.CustomerRepository;
import org.fdifrison.micro.accounts.service.ICustomerService;
import org.fdifrison.micro.accounts.service.client.CardsFeignClient;
import org.fdifrison.micro.accounts.service.client.LoansFeignClient;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    public CustomerService(AccountRepository accountRepository, CustomerRepository customerRepository,
                           CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        var customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );
        var account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        var loans = loansFeignClient.fetchLoansDetails(correlationId, mobileNumber);
        var cards = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);

        var customerDetails = CustomerMapper.mapToCustomerDetailDTO(customer, new CustomerDetailsDto());

        customerDetails.setAccountDTO(AccountMapper.mapToAccountDTO(account));

        // We have implemented the Feign fall back to return null in case of an open circuitbreaker therefore,
        // we need to check for null-pointers
        customerDetails.setLoanDTO(loans != null ? loans.getBody() : null);
        customerDetails.setCardDTO(cards != null ? cards.getBody(): null);

        return customerDetails;
    }
}
