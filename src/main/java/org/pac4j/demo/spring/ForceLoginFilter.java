package org.pac4j.demo.spring;

import org.pac4j.core.client.Client;
import org.pac4j.core.config.Config;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.exception.HttpAction;

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

        final J2EContext context = new J2EContext(request, response);
        final Client client = config.getClients().findClient(request.getParameter(Pac4jConstants.DEFAULT_CLIENT_NAME_PARAMETER));
        try {
            client.redirect(context);
        } catch (final HttpAction e) {
        }
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
