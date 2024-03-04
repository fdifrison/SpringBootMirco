package org.fdifrison.micro.servicediscoveryagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDiscoveryAgentApplication.class, args);
    }

}
