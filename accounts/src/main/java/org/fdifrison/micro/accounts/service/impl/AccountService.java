package org.fdifrison.micro.accounts.service.impl;

import org.fdifrison.micro.accounts.constants.AccountConstants;
import org.fdifrison.micro.accounts.dto.AccountDTO;
import org.fdifrison.micro.accounts.dto.AccountMsgDto;
import org.fdifrison.micro.accounts.dto.CustomerDTO;
import org.fdifrison.micro.accounts.entity.Account;
import org.fdifrison.micro.accounts.entity.Customer;
import org.fdifrison.micro.accounts.exception.CustomerAlreadyExistException;
import org.fdifrison.micro.accounts.exception.ResourceNotFoundException;
import org.fdifrison.micro.accounts.mapper.AccountMapper;
import org.fdifrison.micro.accounts.mapper.CustomerMapper;
import org.fdifrison.micro.accounts.repository.AccountRepository;
import org.fdifrison.micro.accounts.repository.CustomerRepository;
import org.fdifrison.micro.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountService implements IAccountService {

    private final static Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    private final StreamBridge streamBridge;


    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository, StreamBridge streamBridge) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public void creatAccount(CustomerDTO customerDTO) {
        var customer = CustomerMapper.mapToCustomer(customerDTO);
        var optionalCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (optionalCustomer.isPresent())
            throw new CustomerAlreadyExistException("Costumer already registered with the same mobile number: " + customerDTO.getMobileNumber());
        var savedCustomer = customerRepository.save(customer);
        var savedAccount = accountRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Account account, Customer customer) {
        var accountMsgDTO = new AccountMsgDto(account.getAccountNumber(), customer.getName(), customer.getEmail(), customer.getMobileNumber());
        logger.info("Sending communication request for: {}", accountMsgDTO);
        var result = streamBridge.send("sendCommunication-out-0", accountMsgDTO);
        logger.info("Communication request processed correctly? -> {}", result);
    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        var customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );
        var account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        var customerDTO = CustomerMapper.mapToCustomerDTO(customer);
        customerDTO.setAccountDTO(AccountMapper.mapToAccountDTO(account));
        return customerDTO;
    }

    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated = false;
        AccountDTO accountDTO = customerDTO.getAccountDTO();
        if (accountDTO != null) {
            Account account = accountRepository.findById(accountDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountDTO.getAccountNumber().toString())
            );
            accountRepository.save(AccountMapper.mapToAccount(accountDTO, account));

            Long customerId = account.getCustomerId();
            var customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );

            customerRepository.save(CustomerMapper.mapToCustomer(customerDTO, customer));

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdate = false;
        if (accountNumber != null) {
            var account = accountRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdate = true;
        }
        return isUpdate;
    }
}
