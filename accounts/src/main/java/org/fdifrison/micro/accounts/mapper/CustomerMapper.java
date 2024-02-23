package org.fdifrison.micro.accounts.mapper;

import org.fdifrison.micro.accounts.dto.CustomerDTO;
import org.fdifrison.micro.accounts.entity.Customer;


public class CustomerMapper {


    public static CustomerDTO mapToCustomerDTO(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());

        return customerDTO;
    }

    public static CustomerDTO mapToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        if (customer == null) {
            return null;
        }

        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());

        return customerDTO;
    }



    public static Customer mapToCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());

        return customer;
    }

    public static Customer mapToCustomer(CustomerDTO customerDTO, Customer customer) {
        if (customerDTO == null) {
            return null;
        }

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setMobileNumber(customerDTO.getMobileNumber());

        return customer;
    }

}
