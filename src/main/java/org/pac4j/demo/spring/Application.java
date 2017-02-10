package org.pac4j.demo.spring;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Application {

    @RequestMapping("/roleadmin/index.html")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String roleAdmin() {
        return "protectedIndex";
    }
}
