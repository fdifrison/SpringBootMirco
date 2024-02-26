package org.fdifrison.micro.loans.mapper;

import org.fdifrison.micro.loans.dto.LoanDTO;
import org.fdifrison.micro.loans.entity.Loan;


public class LoansMapper {


    public static LoanDTO mapToLoanDTO(Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanDTO loanDTO = new LoanDTO();

        return setLoanDTOFields(loan, loanDTO);
    }

    public static LoanDTO mapToLoanDTO(Loan loan, LoanDTO loanDTO) {
        if (loan == null) {
            return null;
        }

        return setLoanDTOFields(loan, loanDTO);
    }

    private static LoanDTO setLoanDTOFields(Loan loan, LoanDTO loanDTO) {
        loanDTO.setMobileNumber(loan.getMobileNumber());
        loanDTO.setLoanType(loan.getLoanType());
        loanDTO.setLoanNumber(loan.getLoanNumber());
        loanDTO.setTotalLoan(loan.getTotalLoan());
        loanDTO.setAmountPaid(loan.getAmountPaid());
        loanDTO.setOutstandingAmount(loan.getOutstandingAmount());

        return loanDTO;
    }

    public static Loan mapToLoan(LoanDTO loanDTO) {
        if (loanDTO == null) {
            return null;
        }

        Loan loan = new Loan();

        return setLoanFields(loanDTO, loan);
    }

    public static Loan mapToLoan(LoanDTO loanDTO, Loan loan) {
        if (loanDTO == null) {
            return null;
        }

        return setLoanFields(loanDTO, loan);
    }

    private static Loan setLoanFields(LoanDTO loanDTO, Loan loan) {
        loan.setMobileNumber(loanDTO.getMobileNumber());
        loan.setLoanType(loanDTO.getLoanType());
        loan.setLoanNumber(loanDTO.getLoanNumber());
        loan.setTotalLoan(loanDTO.getTotalLoan());
        loan.setAmountPaid(loanDTO.getAmountPaid());
        loan.setOutstandingAmount(loanDTO.getOutstandingAmount());

        return loan;
    }


}
