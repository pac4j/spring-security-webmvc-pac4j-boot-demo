package org.pac4j.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, MongoAutoConfiguration.class })
public class SpringBootPac4jDemo {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootPac4jDemo.class, args);
    }
}
