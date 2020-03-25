package org.pac4j.demo.spring;

import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.UserProfile;

import java.util.Optional;

public class RoleAdminAuthGenerator implements AuthorizationGenerator {

    @Override
    public Optional<UserProfile> generate(final WebContext context, final UserProfile profile) {
        profile.addRole("ROLE_ADMIN");
        return Optional.of(profile);
    }
}
