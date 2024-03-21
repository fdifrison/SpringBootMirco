# SpringBootMirco
Intro to Microservices with SpringBoot

## Tech Stack

* Three Microservices in Spring Boot:
  * Accounts
  * Cards
  * Loans
* Docker containerization
* Kubernetes for orchestration (in replacement to Eureka and Config-Server)
* Helm for K8s management
* MySQL as RDBMS
* Security
  * Spring Security
  * OAuth2 & OpenID connect
  * Keycloak
* Logging & Monitoring
  * Spring Actuator for health endpoints
  * Resilience4j for resiliency patterns
    * circuit brake
    * retries
    * limiter
  * Grafana (main dashboard)
  * Prometheus
  * Loki
  * Tempo
* Spring Cloud
  * Eureka Server as service discovery agent
  * Config Server for config externalization
    * Redis for config update
  * Gateway Server for API gateway (single entry point)
* Messaging
  * Spring Cloud Function
  * Spring Cloud Streams
    * RabbitMQ
    * Kafka
