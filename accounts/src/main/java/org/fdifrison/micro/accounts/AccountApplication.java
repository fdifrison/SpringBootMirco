package org.fdifrison.micro.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.fdifrison.micro.accounts.config.AppContactInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = AppContactInfo.class)
@OpenAPIDefinition(
        info = @Info(
                title = "Account microservice REST API documentation",
                description = "A simple description of the API",
                version = "v1",
                contact = @Contact(
                        name = "Fdifrison",
                        email = "ing.giovanni.frison@gmail.com"),
                license = @License(
                        name = "SuperDuper Licence"
                )),
        externalDocs = @ExternalDocumentation(
                url = "https://github.com/fdifrison/SpringBootMirco"
        ))
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
