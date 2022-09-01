package org.pac4j.demo.spring;

import org.pac4j.core.config.Config;
import org.pac4j.jee.http.adapter.JEEHttpActionAdapter;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.pac4j.springframework.web.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
// for callback and logout controllers
@ComponentScan(basePackages = "org.pac4j.springframework.web")
public class MvcSecurityConfig implements WebMvcConfigurer {

    @Autowired
    private Config config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(buildInterceptor("TwitterClient")).addPathPatterns("/twitter/*");
        registry.addInterceptor(buildInterceptor("CasClient")).addPathPatterns("/cas/*");
        registry.addInterceptor(buildInterceptor("DirectBasicAuthClient")).addPathPatterns("/dba/*");
    }

    private SecurityInterceptor buildInterceptor(final String client) {
        return SecurityInterceptor.build(config, client, JEEHttpActionAdapter.INSTANCE);
    }
}
