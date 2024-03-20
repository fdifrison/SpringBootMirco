package org.fdifrison.micro.accounts.service;

import org.fdifrison.micro.accounts.dto.CustomerDTO;

public interface IAccountService {

    void creatAccount(CustomerDTO customerDTO);

    CustomerDTO fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);

    boolean deleteAccount(String mobileNumber);

    boolean updateCommunicationStatus(Long accountNumber);

}
