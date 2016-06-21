package org.vaadin.example.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class OAuthConfiguration {

    @Autowired
    OAuthProperties oauthProperties;

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CurrentUser currentUser() {
        return new CurrentUser(oauthProperties);
    }

    @Bean
    public OAuthFilter oauthFilter() {
        return new OAuthFilter(currentUser(), oauthProperties);
    }

    @Bean
    public FilterRegistrationBean oauthFilterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(oauthFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
