package org.pac4j.demo.spring;

import org.pac4j.core.config.Config;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jee.context.JEEContext;
import org.pac4j.jee.context.session.JEESessionStore;
import org.pac4j.jee.http.adapter.JEEHttpActionAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class Application {

    private static final String PROFILES = "profiles";
    private static final String CONTEXT = "context";
    private static final String SESSION_ID = "sessionId";

    @Autowired
    private Config config;

    @Autowired
    private JEEContext jeeContext;

    @Autowired
    private ProfileManager profileManager;

    @RequestMapping("/")
    public String root(Map<String, Object> map) throws HttpAction {
        return index(map);
    }

    @RequestMapping("/index.html")
    public String index(Map<String, Object> map) throws HttpAction {
        map.put(PROFILES, profileManager.getProfiles());
        map.put(CONTEXT, SecurityContextHolder.getContext());
        map.put(SESSION_ID, JEESessionStore.INSTANCE.getSessionId(jeeContext, false).orElse("nosession"));
        return "index";
    }

    @RequestMapping("/login.html")
    public String login(Map<String, Object> map) {
        return "loginform";
    }

    @RequestMapping(value = {"/twitter/index.html", "/cas/index.html", "/dba/index.html",
            "/protected/index.html", "/admin/index.html", "/login/index.html"})
    public String twitter(Map<String, Object> map) {
        map.put(PROFILES, profileManager.getProfiles());
        map.put(CONTEXT, SecurityContextHolder.getContext());
        return "protectedIndex";
    }

    @ExceptionHandler(HttpAction.class)
    public void httpAction(final HttpAction action) {
        JEEHttpActionAdapter.INSTANCE.adapt(action, jeeContext);
    }
}
