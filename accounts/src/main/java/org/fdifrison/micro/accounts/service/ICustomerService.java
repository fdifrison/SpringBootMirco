package org.fdifrison.micro.accounts.service;

import org.fdifrison.micro.accounts.dto.external.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
