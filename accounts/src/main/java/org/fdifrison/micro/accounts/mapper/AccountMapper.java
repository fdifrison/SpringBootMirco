package org.fdifrison.micro.accounts.mapper;

import org.fdifrison.micro.accounts.dto.AccountDTO;
import org.fdifrison.micro.accounts.entity.Account;


public class AccountMapper {


    public static AccountDTO mapToAccountDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBranchAddress(account.getBranchAddress());

        return accountDTO;
    }

    public static AccountDTO mapToAccountDTO(Account account, AccountDTO accountDTO) {
        if (account == null) {
            return null;
        }

        accountDTO.setAccountNumber(account.getAccountNumber());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setBranchAddress(account.getBranchAddress());

        return accountDTO;
    }



    public static Account mapToAccount(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }

        Account account = new Account();

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBranchAddress(accountDTO.getBranchAddress());

        return account;
    }

    public static Account mapToAccount(AccountDTO accountDTO, Account account) {
        if (accountDTO == null) {
            return null;
        }

        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountType(accountDTO.getAccountType());
        account.setBranchAddress(accountDTO.getBranchAddress());

        return account;
    }
}
