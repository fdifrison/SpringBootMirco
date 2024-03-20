package org.fdifrison.micro.accounts.functions;

import org.fdifrison.micro.accounts.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunctions {

    private final static Logger logger = LoggerFactory.getLogger(AccountFunctions.class);

    // We need a consumer
    // that takes an account number from the sms
    // sent by the message ms and that will trigger an operation to db
    // to update the status of the account creation process
    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountService) {
        return accountNumber -> {
          logger.info("Updating Communication status for account {}", accountNumber);
          accountService.updateCommunicationStatus(accountNumber);
        };
    }

}
