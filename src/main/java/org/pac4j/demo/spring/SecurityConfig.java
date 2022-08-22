package org.pac4j.demo.spring;

import org.pac4j.core.config.Config;
import org.pac4j.springframework.security.web.CallbackFilter;
import org.pac4j.springframework.security.web.LogoutFilter;
import org.pac4j.springframework.security.web.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig {

/*    @Configuration
    @Order(1)
    public static class TwitterWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "TwitterClient");

            http
                    .antMatcher("/twitter/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }*/

    @Configuration
    @Order(2)
    public static class CasWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "CasClient");

            http
                    .antMatcher("/cas/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(3)
    public static class ProtectedWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config);

            http
                    .antMatcher("/protected/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        }
    }

    @Configuration
    @Order(4)
    public static class DbaWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final SecurityFilter filter = new SecurityFilter(config, "DirectBasicAuthClient");

            http
                    .antMatcher("/dba/**")
                    .addFilterBefore(filter, BasicAuthenticationFilter.class)
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        }
    }
/*
    @Configuration
    @Order(5)
    public static class CallbackWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final CallbackFilter callbackFilter = new CallbackFilter(config);

            http
                    .antMatcher("/callback*")
                    .addFilterBefore(callbackFilter, BasicAuthenticationFilter.class)
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(6)
    public static class LogoutWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            final LogoutFilter logoutFilter = new LogoutFilter(config, "/?defaulturlafterlogout");
            logoutFilter.setDestroySession(true);

            http
                    .antMatcher("/pac4jLogout")
                    .addFilterBefore(logoutFilter, BasicAuthenticationFilter.class)
                    .csrf().disable();
        }
    }
*/
    @Configuration
    @Order(7)
    public static class SpringSecurityWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private Config config;

        protected void configure(final HttpSecurity http) throws Exception {

            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/login/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/perform_login")
                    .defaultSuccessUrl("/index.html", false)
                    .failureUrl("/login.html?error=true")
                    .and()
                    .logout().logoutSuccessUrl("/");
        }

        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
                    .and()
                    .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
