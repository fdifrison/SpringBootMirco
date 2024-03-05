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

    public CustomerService(AccountRepository accountRepository, CustomerRepository customerRepository, CardsFeignClient cardsFeignClient, LoansFeignClient loansFeignClient) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.cardsFeignClient = cardsFeignClient;
        this.loansFeignClient = loansFeignClient;
    }

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        var customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );
        var account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        var loans = loansFeignClient.fetchLoansDetails(mobileNumber).getBody();
        var cards = cardsFeignClient.fetchCardDetails(mobileNumber).getBody();

        var customerDetails = CustomerMapper.mapToCustomerDetailDTO(customer, new CustomerDetailsDto());

        customerDetails.setAccountDTO(AccountMapper.mapToAccountDTO(account));
        customerDetails.setLoanDTO(loans);
        customerDetails.setCardDTO(cards);

        return customerDetails;
    }
}
