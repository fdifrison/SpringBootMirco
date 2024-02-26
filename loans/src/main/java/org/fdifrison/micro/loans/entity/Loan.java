package org.fdifrison.micro.loans.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long loanId;
    @NotNull
    String mobileNumber;
    @NotNull
    String loanNumber;
    @NotNull
    String loanType;
    @NotNull
    int totalLoan;
    @NotNull
    int amountPaid;
    @NotNull
    int outstandingAmount;

}
