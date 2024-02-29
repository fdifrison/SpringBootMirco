package org.fdifrison.micro.loans.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public final class AppContactInfo {
    private  String message;
    private  Map<String, String> contactDetails;
    private  List<String> onCallSupport;

}

