package org.pac4j.demo.spring;

import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.util.Pac4jConstants;
import org.pac4j.jee.context.JEEContext;
import org.pac4j.jee.context.session.JEESessionStore;
import org.pac4j.jee.http.adapter.JEEHttpActionAdapter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForceLoginFilter implements Filter {

    private Config config;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        final JEEContext context = new JEEContext(request, response);
        final Client client = config.getClients().findClient(request.getParameter(Pac4jConstants.DEFAULT_CLIENT_NAME_PARAMETER)).get();
        HttpAction action;
        try {
            action = (HttpAction) client.getRedirectionAction(context, JEESessionStore.INSTANCE).get();
        } catch (final HttpAction e) {
            action = e;
        }
        JEEHttpActionAdapter.INSTANCE.adapt(action, context);
    }

    @Override
    public void destroy() { }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
