<p align="center">
  <img src="https://pac4j.github.io/pac4j/img/logo-spring-security.png" width="300" />
</p>

This `spring-security-pac4j-demo` project is a Spring Security web application to test the [spring-security-pac4j](https://github.com/pac4j/spring-security-pac4j) security extension with various authentication mechanisms: Facebook, Twitter, form, basic auth, CAS, SAML...


## Start & test

Build the project and launch the web app with jetty on [http://localhost:8080](http://localhost:8080):

    cd spring-security-pac4j-demo
    mvn clean compile jetty:run

To test, you can call a protected url by clicking on the "Protected url by **xxx**" link, which will start the authentication process with the **xxx** provider.
