package org.fdifrison.micro.loans.service.impl;

import org.fdifrison.micro.loans.constants.LoanConstants;
import org.fdifrison.micro.loans.dto.LoanDTO;
import org.fdifrison.micro.loans.entity.Loan;
import org.fdifrison.micro.loans.exception.LoanAlreadyExistException;
import org.fdifrison.micro.loans.exception.ResourceNotFoundException;
import org.fdifrison.micro.loans.mapper.LoansMapper;
import org.fdifrison.micro.loans.repository.LoanRepository;
import org.fdifrison.micro.loans.service.ILoanService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoanService implements ILoanService {

    private final LoanRepository repository;

    public LoanService(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createLoan(String mobileNumber) {
        repository.findByMobileNumber(mobileNumber).ifPresentOrElse(loan -> {
            throw new LoanAlreadyExistException("Loan already registered with the given mobile number: " + loan.getMobileNumber());
        }, () -> repository.save(createNewLoan(mobileNumber)));
    }

    private Loan createNewLoan(String mobileNumber) {
        Loan newLoan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoanConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public LoanDTO fetchLoan(String mobileNumber) {
        return repository.findByMobileNumber(mobileNumber)
                .map(LoansMapper::mapToLoanDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));

    }

    @Override
    public boolean updateLoan(LoanDTO loanDTO) {
        repository.findByLoanNumber(loanDTO.getLoanNumber())
                .ifPresentOrElse(loan -> repository.save(LoansMapper.mapToLoan(loanDTO, loan)), () -> {
            throw new ResourceNotFoundException("Loan", "loanNumber", loanDTO.getLoanNumber());
        });
        return true;
    }

    @Override
    public boolean deleteLoan(String mobileNumber) {
        repository.findByMobileNumber(mobileNumber).ifPresentOrElse(repository::delete, () -> {
            throw new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber);
        });
        return true;
    }
}
