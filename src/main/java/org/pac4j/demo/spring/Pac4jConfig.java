package org.pac4j.demo.spring;

import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.client.direct.AnonymousClient;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.oauth.client.TwitterClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class Pac4jConfig {

    @Bean
    public Config config() {
        final TwitterClient twitterClient = new TwitterClient("CoxUiYwQOSFDReZYdjigBA",
                "2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");

        final CasConfiguration casConfiguration = new CasConfiguration("https://casserverpac4j.herokuapp.com/login");
        final CasClient casClient = new CasClient(casConfiguration);
        casClient.setAuthorizationGenerator( (ctx, session, profile) -> {
            profile.addRole("ROLE_ADMIN");
            return Optional.of(profile);
        });

        final SimpleTestUsernamePasswordAuthenticator simpleTestUsernamePasswordAuthenticator = new SimpleTestUsernamePasswordAuthenticator();
        final DirectBasicAuthClient directBasicAuthClient = new DirectBasicAuthClient(simpleTestUsernamePasswordAuthenticator);

        final AnonymousClient anonymousClient = new AnonymousClient();

        final Clients clients = new Clients("http://localhost:8080/callback", twitterClient, casClient, directBasicAuthClient, anonymousClient);

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        return config;
    }
}
