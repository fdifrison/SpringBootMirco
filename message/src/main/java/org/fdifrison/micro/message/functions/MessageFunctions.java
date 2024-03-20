package org.fdifrison.micro.message.functions;

import org.fdifrison.micro.message.dto.AccountsMsgDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

// All this functions will be exposed as rest apis thanks to spring cloud function web
@Configuration
public class MessageFunctions {

    private final static Logger logger = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<AccountsMsgDTO, AccountsMsgDTO> email() {
        return accountsMsgDTO -> {
            logger.info("Sending email: " + accountsMsgDTO.toString());
            return accountsMsgDTO;
        };
    }

    @Bean
    public Function<AccountsMsgDTO, Long> sms() {
        return accountsMsgDTO -> {
            logger.info("Sending sms: " + accountsMsgDTO.toString());
            return accountsMsgDTO.accountNumber();
        };
    }

}
