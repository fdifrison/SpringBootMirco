package org.fdifrison.micro.accounts.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public final class AppContactInfo {
    private  String message;
    private  Map<String, String> contactDetails;
    private  List<String> onCallSupport;

}
