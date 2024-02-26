package org.fdifrison.micro.loans.service;

import org.fdifrison.micro.loans.dto.LoanDTO;

public interface ILoanService {

    void createLoan(String mobileNumber);

    LoanDTO fetchLoan(String mobileNumber);

    boolean updateLoan(LoanDTO loanDTO);

    boolean deleteLoan(String mobileNumber);

}
