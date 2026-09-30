<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-spring-security.png" width="300" />
</p>

> This demo secures a Spring Security application with **[spring-security-pac4j](https://github.com/pac4j/spring-security-pac4j)**, the Spring Security integration of **[pac4j](https://github.com/pac4j/pac4j)**, the security engine for Java.
> If it is useful to you, please ⭐ **[star pac4j on GitHub](https://github.com/pac4j/pac4j)**: it helps other developers discover it!

This `spring-security-webmvc-pac4j-boot-demo` project is a Spring Security boot demo using:
- Spring Security + Spring Boot
- the [spring-webmvc-pac4j](https://github.com/pac4j/spring-webmvc-pac4j) security library
- the [spring-security-pac4j](https://github.com/pac4j/spring-security-pac4j) bridge from pac4j to Spring Security.

## Run and test

You can build the project and run it on [http://localhost:8080](http://localhost:8080) using the following commands:

    cd spring-security-webmvc-pac4j-boot-demo
    mvn clean compile exec:java

or

    cd spring-security-webmvc-pac4j-boot-demo
    mvn spring-boot:run
