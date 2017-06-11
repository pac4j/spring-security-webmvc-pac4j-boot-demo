package org.pac4j.demo.spring;

import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;

public class RoleAdminAuthGenerator implements AuthorizationGenerator<CommonProfile> {

    @Override
    public CommonProfile generate(final WebContext context, final CommonProfile profile) {
        profile.addRole("ROLE_ADMIN");
        return profile;
    }
}
